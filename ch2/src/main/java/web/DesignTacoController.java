package web;

import com.example.ch2.Ingredient;
import com.example.ch2.Ingredient.Type;
import com.example.ch2.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j   // <--1
@Controller  // <--2
@RequestMapping("/design")  // <--3
public class DesignTacoController {

    @GetMapping  // <--4
    public String showDesignForm(Model model) {
        
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Type.values();
        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }

        // Model is an object that ferries data
        // between a controller and whatever view is charged with rendering that data.
        // and it is accessed like an object in js i.e model.id
        // is stored as a Map
        model.addAttribute("design", new Taco());

        // Ultimately,
        // data that’s placed in Model attributes is copied into the servlet response attributes,
        // where the view can find them

        return "design"; // logical name of the view that will render model to browser
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    // handling post requests
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors) { // <--5

        if (errors.hasErrors()) {
            return "design";  // if errors just redirect to '/design'
        }
        // save taco design
        // will do in ch3

        log.info("Processing design: " + design);

        return "redirect:/orders/current";
        // indicating that this is a redirect view.
        // More specifically, it indicates that after processDesign() completes, the user’s browser
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