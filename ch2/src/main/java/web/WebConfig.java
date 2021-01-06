package web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Controller
//public class HomeController {
//
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
//}

// when a controller is
// simple enough that it doesn’t populate a model or process input—as is the case here
// there’s another way that you can define the controller. Have a look at WebConfig
// to see how you can declare a view controller that does nothing but forward the request to a view.

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
