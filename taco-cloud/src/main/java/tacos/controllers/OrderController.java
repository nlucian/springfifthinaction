package tacos.controllers;

import lombok.extern.slf4j.Slf4j;
import tacos.data.JdbcOrderRepository;
import tacos.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private JdbcOrderRepository jdbcOrderRepository;

	@Autowired
	public OrderController(JdbcOrderRepository jdbcOrderRepository) {
		this.jdbcOrderRepository = jdbcOrderRepository;
	}

	@GetMapping("/current")
	public String orderController(Model model) {
		return "orderForm";
	}

	@PostMapping()
	public String processOrders(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		jdbcOrderRepository.save(order);
		log.debug("Order saved: " + order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
