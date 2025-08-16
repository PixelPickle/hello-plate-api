package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

@Data
public class Ingredient {

    private String id;
    private String uuid;
    private String name;
    private String type;
    private String slug;

    private IngredientFamily family;

    private boolean shipped;

    private String imageLink;
    private String imagePath;

}
