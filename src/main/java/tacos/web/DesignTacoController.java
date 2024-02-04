package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        /*List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO","Flour Tortilla", Type.WRAP),
                new Ingredient("COTO","Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF","Ground Beef", Type.PROTEIN),
                new Ingredient("CARN","Carnitas", Type.PROTEIN),
                new Ingredient("TMTO","Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC","Lettuce", Type.VEGGIES),
                new Ingredient("CHED","Cheddar", Type.CHEESE),
                new Ingredient("JACK","Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA","Salsa", Type.SAUCE),
                new Ingredient("SRCR","Sour Cream", Type.SAUCE)
        );*/

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("taco", new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors) {

        if (errors.hasErrors()) {
            return "design";
        }

        // 이 지점에서 타코 디자인(선택된 식자재 내역)을  저장한다.
        // 이 작업은 3장에서 할 것이다.
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }
}
