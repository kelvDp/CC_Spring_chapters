package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j   // <--1
@Controller  // <--2
@RequestMapping("/design")  // <--3
@SessionAttributes("order") // <--6
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    @ModelAttribute(name = "order") // ensures that an Order object will be created in the model
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design") // ensures that a Taco object will be created in the model
    public Taco taco() {
        return new Taco();
    }

    @GetMapping  // <--4
    public String showDesignForm(Model model) {
        
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );
        // instead of hardcoding the above, can now use DB to persist data
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredient -> ingredients.add(ingredient));

        Type[] types = Type.values();
        // add the data to the model
        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }

//        model.addAttribute("design", new Taco());

        return "design"; // logical name of the view that will render model to browser
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }


    // handling post requests
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) { // <--5

        if (errors.hasErrors()) {
            log.info(errors.toString());
            return "design";  // if errors just redirect to '/design'
        }

        // save taco design
        Taco saved = tacoRepo.save(design);

        order.addDesign(saved);

//        log.info("Processing design: " + design);

        return "redirect:/orders/current";
        // indicating that this is a redirect view.
        // it indicates that after processDesign() completes, the user’s browser
        // should be redirected to the relative path /order/current.
    }

}

// 1. will at runtime generate a logger in class
// 2. identiffies class as a controller + marks it as candidate for component scanning,
// so that Spring will discover it and automatically create an instance of it as a bean in the Spring application context.
// 3.when applied at the class level, specifies the kind of requests that this controller handles.
// In this case, it specifies that DesignTacoController will handle requests whose path begins with /design.
// 4. @GetMapping, paired with the classlevel @RequestMapping, specifies that when an HTTP GET request is received
// for '/design', showDesignForm() will be called to handle the request.
// 5. The @Valid annotation tells Spring MVC to perform validation on the submitted Taco
// object after it’s bound to the submitted form data and before the processDesign()
// method is called. If there are any validation errors, the details of those errors will be
// captured in an Errors object that’s passed into processDesign()
// @ModelAttribute to indicate that its
//value should come from the model and that Spring MVC shouldn’t attempt to bind
//request parameters to it.

// 6. you need the order to be present across multiple requests
// so that you can create multiple tacos and add them to the order. The class-level @SessionAttributes
// annotation specifies any model objects like the order attribute
// that should be kept in session and available across multiple requests.