package com.squirrelly_app.hello_plate_api.service;

import com.squirrelly_app.hello_plate_api.exception.InvalidRecipeId;
import com.squirrelly_app.hello_plate_api.exception.InvalidResponseException;
import com.squirrelly_app.hello_plate_api.exception.RequiredValueException;
import com.squirrelly_app.hello_plate_api.model.document.*;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.Response;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.MenuCourse;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.MenuCourseRecipe;
import com.squirrelly_app.hello_plate_api.util.LogUtil;
import com.squirrelly_app.hello_plate_api.util.UrlUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class ImportService {

    private final Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final RestTemplate restTemplate;
    private final DatabaseService databaseService;

    public ImportService(RestTemplate restTemplate, DatabaseService databaseService) {
        this.restTemplate = restTemplate;
        this.databaseService = databaseService;
    }

    public ResponseEntity<Void> importMenu(String magic, String year, String week) {

        Long callId = new Date().getTime();
        String callTag = "ImportService.importMenu";

        LogUtil.postRequest(logger, callId, callTag, String.format("%s/%s/%s", magic, year, week));

        try {

            if (magic == null || year == null || week == null) {
                throw new  RequiredValueException("Required parameters missing");
            }

            String url = UrlUtil.getMenuUrl(magic, year, week);

            Response response = restTemplate.getForObject(url, Response.class);

            if (response == null || response.getPageProps() == null || response.getPageProps().getSsrPayload() == null || response.getPageProps().getSsrPayload().getCourses() == null || response.getPageProps().getSsrPayload().getCourses().isEmpty()) {
                throw new InvalidResponseException("Menu Value is Invalid");
            }

            List<MenuCourseRecipe> rawRecipes = response.getPageProps().getSsrPayload().getCourses().stream()
                    .map(MenuCourse::getRecipe)
                    .filter(Objects::nonNull)
                    .toList();

            LogUtil.postResponse(logger, callId, callTag, String.format("Found %s Recipes...", rawRecipes.size()));

            List<MenuCourseRecipe> populatedRecipes = populateAllRecipes(rawRecipes, magic, callId, callTag);

            importAllRecipesFromMenu(populatedRecipes, callId, callTag);
            importAllIngredientsFromMenu(populatedRecipes, callId, callTag);
            importAllIngredientFamiliesFromMenu(populatedRecipes, callId, callTag);
            importAllCategoriesFromMenu(populatedRecipes, callId, callTag);
            importAllCuisinesFromMenu(populatedRecipes, callId, callTag);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RequiredValueException exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Void> importRecipe(String magic, String recipeId) {

        Long callId = new Date().getTime();
        String callTag = "ImportService.importRecipe";

        LogUtil.postRequest(logger, callId, callTag, String.format("%s/%s", magic, recipeId));

        try {

            if (magic == null || recipeId == null) {
                throw new  RequiredValueException("Required parameters missing");
            }

            Optional<Recipe> recipe = databaseService.recipeRepository.findById(recipeId);

            if (recipe.isEmpty()) {
                throw new InvalidRecipeId(recipeId);
            }

            String url = UrlUtil.getRecipeUrl(magic, recipe.get());

            Response response = restTemplate.getForObject(url, Response.class);

            if (response == null || response.getPageProps() == null || response.getPageProps().getSsrPayload() == null || response.getPageProps().getSsrPayload().getRecipe() == null) {
                throw new InvalidResponseException("Response Value is Invalid");
            }

            MenuCourseRecipe rawRecipe = response.getPageProps().getSsrPayload().getRecipe();

            LogUtil.postResponse(logger, callId, callTag, rawRecipe.toString());

            importRecipe(rawRecipe, callId, callTag);
            importAllIngredientsFromRecipe(rawRecipe, callId, callTag);
            importAllIngredientFamiliesFromRecipe(rawRecipe, callId, callTag);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RequiredValueException exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private List<MenuCourseRecipe> populateAllRecipes(@NotNull List<MenuCourseRecipe> recipes, @NotNull String magic, @NotNull Long callId, @NotNull String callTag) {

        List<MenuCourseRecipe> populatedRecipes = new ArrayList<>();

        for (MenuCourseRecipe recipe : recipes) {

            LogUtil.postRequest(logger, callId, callTag, String.format("Populating %s", recipe.getName()));

            try {

                String url = UrlUtil.getRecipeUrl(magic, recipe);

                Response response = restTemplate.getForObject(url, Response.class);

                if (response == null || response.getPageProps() == null || response.getPageProps().getSsrPayload() == null || response.getPageProps().getSsrPayload().getRecipe() == null) {
                    throw new InvalidResponseException("Response Value is Invalid");
                }

                populatedRecipes.add(response.getPageProps().getSsrPayload().getRecipe());

            } catch (Exception exception) {

                LogUtil.postError(logger, callId, callTag, exception.getMessage());

            }

        }

        return populatedRecipes;

    }

    private void importAllRecipesFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<Recipe> preparedRecipes = rawRecipes.stream()
                .map(Recipe::new)
                .filter(distinctByKey(Recipe::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Recipes...", preparedRecipes.size()));

        databaseService.recipeRepository.saveAll(preparedRecipes);

    }

    private void importAllIngredientsFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<Ingredient> preparedIngredients = rawRecipes.stream()
                .map(MenuCourseRecipe::getIngredients)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(Ingredient::new)
                .filter(distinctByKey(Ingredient::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s ingredients...", preparedIngredients.size()));

        databaseService.ingredientRepository.saveAll(preparedIngredients);

    }

    private void importAllIngredientFamiliesFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<IngredientFamily> preparedIngredientFamilies = rawRecipes.stream()
                .map(MenuCourseRecipe::getIngredients)
                .flatMap(List::stream)
                .map(com.squirrelly_app.hello_plate_api.model.hello_fresh.Ingredient::getFamily)
                .filter(Objects::nonNull)
                .map(IngredientFamily::new)
                .filter(distinctByKey(IngredientFamily::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s ingredient families...", preparedIngredientFamilies.size()));

        databaseService.ingredientFamilyRepository.saveAll(preparedIngredientFamilies);

    }

    private void importAllCategoriesFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<Category> preparedCategories = rawRecipes.stream()
                .map(MenuCourseRecipe::getCategory)
                .filter(Objects::nonNull)
                .map(Category::new)
                .filter(distinctByKey(Category::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Categories...",  preparedCategories.size()));

        databaseService.categoryRepository.saveAll(preparedCategories);

    }

    private void importAllCuisinesFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<Cuisine> preparedCuisines = rawRecipes.stream()
                .map(MenuCourseRecipe::getCuisines)
                .flatMap(List::stream)
                .map(Cuisine::new)
                .filter(distinctByKey(Cuisine::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Cuisines...",  preparedCuisines.size()));

        databaseService.cuisineRepository.saveAll(preparedCuisines);

    }

    private void importAllIngredientsFromRecipe(@NotNull MenuCourseRecipe rawRecipe,  @NotNull Long callId, @NotNull String callTag) {

        List<Ingredient> preparedIngredients = rawRecipe.getIngredients().stream()
                .map(Ingredient::new)
                .filter(distinctByKey(Ingredient::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Ingredients...",  preparedIngredients.size()));

        databaseService.ingredientRepository.saveAll(preparedIngredients);

    }

    private void importAllIngredientFamiliesFromRecipe(@NotNull MenuCourseRecipe rawRecipe, @NotNull Long callId, @NotNull String callTag) {

        List<IngredientFamily> preparedIngredientFamilies = rawRecipe.getIngredients().stream()
                .map(com.squirrelly_app.hello_plate_api.model.hello_fresh.Ingredient::getFamily)
                .filter(Objects::nonNull)
                .map(IngredientFamily::new)
                .filter(distinctByKey(IngredientFamily::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Ingredient Families...", preparedIngredientFamilies.size()));

        databaseService.ingredientFamilyRepository.saveAll(preparedIngredientFamilies);

    }

    private void importRecipe(@NotNull MenuCourseRecipe rawRecipe, @NotNull Long callId, @NotNull String callTag) {

        Recipe preparedRecipe = new Recipe(rawRecipe);

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing Recipe: %s", preparedRecipe.getName()));

        databaseService.recipeRepository.save(preparedRecipe);

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
