package com.squirrelly_app.hello_plate_api.controller;

import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import com.squirrelly_app.hello_plate_api.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false, defaultValue = "") String category, @RequestParam(required = false, defaultValue = "") String ingredientId) {
        return recipeService.getAllRecipes(category, ingredientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Void> deleteAllRecipes() {
        return recipeService.deleteAllRecipes();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        return recipeService.deleteRecipeById(id);
    }

}
