package xyz.joystickjury.backend;

import jakarta.el.BeanNameResolver;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import user.User;

@RestController
public class TController {

    @GetMapping("/t")
    public String test(){
        return "Hello";
    }
}
