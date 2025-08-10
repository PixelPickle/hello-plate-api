package com.squirrelly_app.hello_plate_api.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("category")
public class Category {

    @Id
    private String id;

    private String name;
    private String type;
    private String slug;

    @SuppressWarnings("unused")
    public Category() {
    }

    public Category(com.squirrelly_app.hello_plate_api.model.hello_fresh.Category helloFreshCategory) {

        this.id = helloFreshCategory.getId();

        this.name = helloFreshCategory.getName();
        this.type = helloFreshCategory.getType();
        this.slug = helloFreshCategory.getSlug();

    }

}
