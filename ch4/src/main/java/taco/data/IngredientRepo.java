package taco.data;

import org.springframework.data.repository.CrudRepository;
import taco.Ingredient;

public interface IngredientRepo extends CrudRepository<Ingredient, String> {
}
