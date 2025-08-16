package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

@Data
public class IngredientFamily {

    private String id;
    private String uuid;
    private String name;
    private String type;
    private String slug;

    private String iconLink;
    private String iconPath;

}
