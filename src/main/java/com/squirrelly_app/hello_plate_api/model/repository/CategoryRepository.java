package com.squirrelly_app.hello_plate_api.model.repository;

import com.squirrelly_app.hello_plate_api.model.document.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
