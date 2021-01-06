package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
//        model.addAttribute("order", new Order());  <- leave out since using session attribute
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        // save the order
        orderRepo.save(order);

        // reset the session attributes after the save, so when creating a new taco order
        // you begin with a clean slate, otherwise the session will never end and new taco order
        // will start with old taco order details etc
        sessionStatus.setComplete();

//        log.info("Order submitted: " + order);
        return "redirect:/";  // redirects to home page
    }
}
