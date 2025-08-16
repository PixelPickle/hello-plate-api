package com.squirrelly_app.hello_plate_api.model.repository;

import com.squirrelly_app.hello_plate_api.model.document.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {



}
