package com.squirrelly_app.hello_plate_api.service;

import com.mongodb.lang.Nullable;
import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import com.squirrelly_app.hello_plate_api.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DatabaseService databaseService;

    public RecipeService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public ResponseEntity<List<Recipe>> getAllRecipes(@Nullable String category) {

        Long callId = new Date().getTime();
        String callTag = "RecipeService.getAllRecipes";

        LogUtil.getRequest(logger, callId, callTag, String.format("category=%s", category));

        try {

            List<Recipe> recipes;

            if (category == null || category.isEmpty()) {
                recipes = databaseService.recipeRepository.findAll();
            } else {
                recipes = databaseService.recipeRepository.findAllByCategory(category);
            }

            LogUtil.getResponse(logger, callId, callTag, "Found " + recipes.size());

            return new ResponseEntity<>(recipes, HttpStatus.OK);

        } catch (Exception exception) {

            LogUtil.getError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Recipe> getRecipeById(String id) {

        Long callId = new Date().getTime();
        String callTag = "RecipeService.getRecipeById";

        LogUtil.getRequest(logger, callId, callTag, id);

        try {

            Optional<Recipe> result = databaseService.recipeRepository.findById(id);

            LogUtil.getResponse(logger, callId, callTag, String.valueOf(result.orElse(null)));

            return result
                    .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception exception) {

            LogUtil.getError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Void> deleteAllRecipes() {

        Long callId = new Date().getTime();
        String callTag = "RecipeService.deleteAllRecipes";

        LogUtil.deleteRequest(logger, callId, callTag, null);

        try {

            databaseService.recipeRepository.deleteAll();

            LogUtil.deleteResponse(logger, callId, callTag, "Successfully deleted all recipes");

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception exception) {

            LogUtil.deleteError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Void> deleteRecipeById(String id) {

        Long callId = new Date().getTime();
        String callTag = "RecipeService.deleteRecipeById";

        LogUtil.deleteRequest(logger, callId, callTag, id);

        try {

            databaseService.recipeRepository.deleteById(id);

            LogUtil.deleteResponse(logger, callId, callTag, "Successfully deleted recipe with id " + id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception exception) {

            LogUtil.deleteError(logger, callId, callTag, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
