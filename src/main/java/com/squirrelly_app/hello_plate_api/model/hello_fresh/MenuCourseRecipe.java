package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

import java.util.List;

@Data
public class MenuCourseRecipe {

    private String id;
    private String name;
    private String headline;
    private String slug;

    private Category category;
    private List<Cuisine> cuisines;
    private List<Tag> tags;
    private List<Ingredient> ingredients;
    private int difficulty;
    private String prepTime;
    private String totalTime;

    private String websiteUrl;
    private String imageLink;
    private String imagePath;

    private int ratingsCount;
    private int averageRating;
    private int favoritesCount;

}
