package tacos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.domain.Ingredient;
import tacos.domain.Order;
import tacos.domain.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.domain.Taco;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
	}

	@ModelAttribute("order")
	public Order getOrder() {
		return new Order();
	}

	@ModelAttribute("taco")
	public Taco getTaco() {
		return new Taco();
	}

	// good model attribute examples:
	// https://www.boraji.com/spring-4-mvc-modelattribute-annotation-example
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			return "design";
		}

		taco = tacoRepository.save(taco);
		order.addTaco(taco);

		log.info("Processing design: " + taco);
		return "redirect:/orders/current";
	}

	@GetMapping
	public String showDesignForm(Model model, @ModelAttribute Taco taco) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));
		Type[] types = Ingredient.Type.values();

		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		model.addAttribute("taco", taco);
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(f -> f.getType().equals(type)).collect(Collectors.toList());
	}
}