package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacos.data.IngredientRepository;
import tacos.Ingredient.Type;

@SpringBootApplication
public class TacosJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacosJpaApplication.class, args);
	}

	// when using jpa, you don.t have to create sql files to create tables etc
	// the entity class name becomes the table names
	// and here is how you would preload data to the table -->

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository ingredientRepo) {
		return args -> {
			ingredientRepo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
			ingredientRepo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
			ingredientRepo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
			ingredientRepo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
			ingredientRepo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
			ingredientRepo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
			ingredientRepo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
			ingredientRepo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
			ingredientRepo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
			ingredientRepo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		};
	}
}
