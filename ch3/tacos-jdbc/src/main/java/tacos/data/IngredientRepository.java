package tacos.data;

// this ingredient repo needs to perform these three operations:
// 1. Query for all ingredients into a collection of Ingredient objects
// 2. Query for a single Ingredient by its id
// 3. Save an Ingredient object

import tacos.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
