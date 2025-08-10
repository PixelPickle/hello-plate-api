package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

@Data
public class Cuisine {

    private String id;
    private String type;
    private String name;
    private String slug;
    private String iconLink;

}
