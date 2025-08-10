package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

@Data
public class MenuCourse {

    private int index;
    private String shoppableProductId;
    private MenuCourseRecipe recipe;

}
