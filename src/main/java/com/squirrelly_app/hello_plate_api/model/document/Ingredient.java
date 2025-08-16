package com.squirrelly_app.hello_plate_api.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ingredient")
public class Ingredient {

    @Id
    private String id;

    private String uuid;
    private String name;
    private String type;
    private String slug;

    private String family;

    private boolean shipped;

    private String imageLink;
    private String imagePath;

    @SuppressWarnings("unused")
    public Ingredient() {
    }

    public Ingredient(com.squirrelly_app.hello_plate_api.model.hello_fresh.Ingredient helloFreshIngredient) {

        this.id = helloFreshIngredient.getId();
        this.uuid = helloFreshIngredient.getUuid();
        this.name = helloFreshIngredient.getName();
        this.type = helloFreshIngredient.getType();
        this.slug = helloFreshIngredient.getSlug();

        if (helloFreshIngredient.getFamily() != null) {
            this.family = helloFreshIngredient.getFamily().getId();
        }

        this.shipped = helloFreshIngredient.isShipped();

        this.imageLink = helloFreshIngredient.getImageLink();
        this.imagePath = helloFreshIngredient.getImagePath();

    }
}
