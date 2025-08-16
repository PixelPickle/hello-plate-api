package com.squirrelly_app.hello_plate_api.service;

import com.squirrelly_app.hello_plate_api.model.document.*;
import com.squirrelly_app.hello_plate_api.model.repository.*;

import com.squirrelly_app.hello_plate_api.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableMongoRepositories(basePackages = "com.squirrelly_app.hello_plate_api.model.repository")
public class DatabaseService {

    public final RecipeRepository recipeRepository;
    public final CategoryRepository categoryRepository;
    public final CuisineRepository cuisineRepository;
    public final IngredientRepository ingredientRepository;
    public final IngredientFamilyRepository ingredientFamilyRepository;

    public DatabaseService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, CuisineRepository cuisineRepository, IngredientRepository ingredientRepository, IngredientFamilyRepository ingredientFamilyRepository) {

        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.cuisineRepository = cuisineRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientFamilyRepository = ingredientFamilyRepository;

        Logger logger =  LoggerFactory.getLogger(DatabaseService.class);

        List<Recipe> recipes = recipeRepository.findAll();
        LogUtil.getResponse(logger, 0L, "recipeRepository", "Found " + recipes.size());

        List<Category> categories = categoryRepository.findAll();
        LogUtil.getResponse(logger, 0L, "categoryRepository", "Found " + categories.size());

        List<Cuisine> cuisines = cuisineRepository.findAll();
        LogUtil.getResponse(logger, 0L, "cuisineRepository", "Found " + cuisines.size());

        List<Ingredient> ingredients = ingredientRepository.findAll();
        LogUtil.getResponse(logger, 0L, "ingredientRepository", "Found " + ingredients.size());

        List<IngredientFamily> ingredientFamilies = ingredientFamilyRepository.findAll();
        LogUtil.getResponse(logger, 0L, "ingredientFamilyRepository", "Found " + ingredientFamilies.size());

    }

}
