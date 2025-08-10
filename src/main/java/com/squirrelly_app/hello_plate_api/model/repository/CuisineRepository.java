package com.squirrelly_app.hello_plate_api.model.repository;

import com.squirrelly_app.hello_plate_api.model.document.Cuisine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CuisineRepository extends MongoRepository<Cuisine, String> {

}
