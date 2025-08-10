package com.squirrelly_app.hello_plate_api.model.repository;

import com.squirrelly_app.hello_plate_api.model.document.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    // Temporary: For Reference
    @SuppressWarnings("unused")
    @Query("{name: '?0'}")
    Recipe findByName(String name);

    // Temporary: For Reference
    @SuppressWarnings("unused")
    @Query(value = "{difficulty:  '?0'}", fields = "{'name':  1, 'headline':  1}")
    List<Recipe> findAllByDifficulty(int difficulty);

    // Temporary: For Reference
    @Query(value = "{category:  '?0'}")
    List<Recipe> findAllByCategory(String category);

    long count();

}
