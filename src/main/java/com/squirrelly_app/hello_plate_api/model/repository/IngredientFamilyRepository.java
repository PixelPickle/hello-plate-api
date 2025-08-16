package com.squirrelly_app.hello_plate_api.model.repository;

import com.squirrelly_app.hello_plate_api.model.document.IngredientFamily;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientFamilyRepository extends MongoRepository<IngredientFamily, String> {



}
