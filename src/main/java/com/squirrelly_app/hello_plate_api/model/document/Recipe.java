package com.squirrelly_app.hello_plate_api.model.document;

import com.squirrelly_app.hello_plate_api.model.hello_fresh.Cuisine;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.Ingredient;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.MenuCourseRecipe;
import com.squirrelly_app.hello_plate_api.model.hello_fresh.Tag;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("recipe")
public class Recipe {

    @Id
    private String id;

    private String name;
    private String headline;
    private String slug;

    private List<String> ingredients;

    private String category;
    private List<String> cuisines;
    private List<String> tags;
    private int difficulty;
    private String prepTime;
    private String totalTime;

    private String websiteUrl;
    private String imageLink;
    private String imagePath;

    private int ratingsCount;
    private int averageRating;
    private int favoritesCount;

    @SuppressWarnings("unused")
    public Recipe() {
    }

    public Recipe(MenuCourseRecipe  menuCourseRecipe) {

        this.id = menuCourseRecipe.getId();
        this.name = menuCourseRecipe.getName();
        this.headline = menuCourseRecipe.getHeadline();
        this.slug = menuCourseRecipe.getSlug();

        this.ingredients = menuCourseRecipe.getIngredients().stream().map(Ingredient::getId).toList();

        if (menuCourseRecipe.getCategory() != null) {
            this.category = menuCourseRecipe.getCategory().getId();
        }

        if (menuCourseRecipe.getCuisines() != null) {
            this.cuisines = menuCourseRecipe.getCuisines().stream().map(Cuisine::getId).toList();
        }

        if (menuCourseRecipe.getTags() != null) {
            this.tags = menuCourseRecipe.getTags().stream().map(Tag::getId).toList();
        }

        this.difficulty = menuCourseRecipe.getDifficulty();
        this.prepTime = menuCourseRecipe.getPrepTime();
        this.totalTime = menuCourseRecipe.getTotalTime();

        this.websiteUrl = menuCourseRecipe.getWebsiteUrl();
        this.imageLink = menuCourseRecipe.getImageLink();
        this.imagePath = menuCourseRecipe.getImagePath();

        this.ratingsCount = menuCourseRecipe.getRatingsCount();
        this.averageRating = menuCourseRecipe.getAverageRating();
        this.favoritesCount = menuCourseRecipe.getFavoritesCount();

    }

}
