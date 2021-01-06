/*
In the JDBC versions of the repositories, you explicitly declared the methods you
wanted the repository to provide. But with Spring Data, you can extend the Crud-
Repository interface instead. For example, here’s the new IngredientRepository
interface
*/

package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> { // <--1
}

// 1. CrudRepository declares about a dozen methods for CRUD (create, read, update, delete)
// operations. Notice that it’s parameterized, with the first parameter being the
// entity type the repository is to persist, and the second parameter being the type of the
// entity ID property. For IngredientRepository, the parameters should be Ingredient
// and String.