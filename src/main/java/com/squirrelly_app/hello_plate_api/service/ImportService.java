package com.squirrelly_app.hello_plate_api.service;

import com.squirrelly_app.hello_plate_api.exception.InvalidResponseException;
import com.squirrelly_app.hello_plate_api.exception.RequiredValueException;
import com.squirrelly_app.hello_plate_api.model.document.Category;
import com.squirrelly_app.hello_plate_api.model.document.Cuisine;
import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.Menu;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

            Menu menu = restTemplate.getForObject(url, Menu.class);

            if (menu == null || menu.getPageProps() == null || menu.getPageProps().getSsrPayload() == null || menu.getPageProps().getSsrPayload().getCourses() == null || menu.getPageProps().getSsrPayload().getCourses().isEmpty()) {
                throw new InvalidResponseException("Menu Value is Invalid");
            }

            List<MenuCourseRecipe> rawRecipes = menu.getPageProps().getSsrPayload().getCourses().stream()
                    .map(MenuCourse::getRecipe)
                    .filter(Objects::nonNull)
                    .toList();

            LogUtil.postResponse(logger, callId, callTag, String.format("Found %s Recipes...", rawRecipes.size()));

            importAllRecipesFromMenu(rawRecipes, callId, callTag);
            importAllCategoriesFromMenu(rawRecipes, callId, callTag);
            importAllCuisinesFromMenu(rawRecipes, callId, callTag);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RequiredValueException exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception exception) {

            LogUtil.postError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private void importAllRecipesFromMenu(@NotNull List<MenuCourseRecipe> rawRecipes, @NotNull Long callId, @NotNull String callTag) {

        List<Recipe> preparedRecipes = rawRecipes.stream()
                .map(Recipe::new)
                .filter(distinctByKey(Recipe::getId))
                .toList();

        LogUtil.postResponse(logger, callId, callTag, String.format("Importing %s Recipes...", preparedRecipes.size()));

        databaseService.recipeRepository.saveAll(preparedRecipes);

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

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
