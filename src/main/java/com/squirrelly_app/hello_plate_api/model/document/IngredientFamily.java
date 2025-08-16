package com.squirrelly_app.hello_plate_api.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ingredientFamily")
public class IngredientFamily {

    @Id
    private String id;

    private String uuid;
    private String name;
    private String type;
    private String slug;

    private String iconLink;
    private String iconPath;

    @SuppressWarnings("unused")
    public IngredientFamily() {
    }

    public IngredientFamily(com.squirrelly_app.hello_plate_api.model.hello_fresh.IngredientFamily helloFreshIngredientFamily) {

        this.id = helloFreshIngredientFamily.getId();

        this.uuid = helloFreshIngredientFamily.getUuid();
        this.name = helloFreshIngredientFamily.getName();
        this.type = helloFreshIngredientFamily.getType();
        this.slug = helloFreshIngredientFamily.getSlug();

        this.iconLink = helloFreshIngredientFamily.getIconLink();
        this.iconPath = helloFreshIngredientFamily.getIconPath();

    }

}
