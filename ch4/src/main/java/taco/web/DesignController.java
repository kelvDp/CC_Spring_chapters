package taco.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import taco.Ingredient;
import taco.Ingredient.Type;
import taco.Order;
import taco.Taco;
import taco.User;
import taco.data.IngredientRepo;
import taco.data.TacoRepo;
import taco.data.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {

    private final IngredientRepo ingredientRepo;

    private final TacoRepo tacoRepo;

    private final UserRepo userRepo;

    @Autowired
    DesignController(IngredientRepo ingredientRepo, TacoRepo tacoRepo, UserRepo userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String createDesign(Model model, @AuthenticationPrincipal User user) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Type.values();

        for (Type t : types ) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }

        User currUser = userRepo.findByUsername(user.getUsername());

        model.addAttribute("user", currUser);

        return "design";
    }

    @PostMapping
    public String processDesign(Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoRepo.save(design);

        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
