package com.squirrelly_app.hello_plate_api.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cuisine")
public class Cuisine {

    @Id
    private String id;

    private String type;
    private String name;
    private String slug;
    private String iconLink;

    @SuppressWarnings("unused")
    public Cuisine() {
    }

    public Cuisine(com.squirrelly_app.hello_plate_api.model.hello_fresh.Cuisine helloFreshCuisine) {

        this.id = helloFreshCuisine.getId();

        this.type = helloFreshCuisine.getType();
        this.name = helloFreshCuisine.getName();
        this.slug = helloFreshCuisine.getSlug();
        this.iconLink = helloFreshCuisine.getIconLink();

    }

}
