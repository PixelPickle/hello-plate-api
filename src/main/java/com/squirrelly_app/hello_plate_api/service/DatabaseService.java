package com.squirrelly_app.hello_plate_api.service;

import com.squirrelly_app.hello_plate_api.model.document.Category;
import com.squirrelly_app.hello_plate_api.model.document.Cuisine;
import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import com.squirrelly_app.hello_plate_api.model.repository.CategoryRepository;
import com.squirrelly_app.hello_plate_api.model.repository.CuisineRepository;
import com.squirrelly_app.hello_plate_api.model.repository.RecipeRepository;

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

    public DatabaseService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, CuisineRepository cuisineRepository) {

        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.cuisineRepository = cuisineRepository;

        Logger logger =  LoggerFactory.getLogger(DatabaseService.class);

        List<Recipe> recipes = recipeRepository.findAll();
        LogUtil.getResponse(logger, 0L, "recipeRepository", "Found " + recipes.size());

        List<Category> categories = categoryRepository.findAll();
        LogUtil.getResponse(logger, 0L, "categoryRepository", "Found " + categories.size());

        List<Cuisine> cuisines = cuisineRepository.findAll();
        LogUtil.getResponse(logger, 0L, "cuisineRepository", "Found " + cuisines.size());

    }

}
