package com.squirrelly_app.hello_plate_api.model.hello_fresh;

import lombok.Data;

import java.util.List;

@Data
public class Tag {

    private String id;
    private String type;
    private String name;
    private String slug;
    private String colorHandle;
    private String displayLabel;
    private List<String> preferences;

}
