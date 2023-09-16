package xyz.joystickjury.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TController {

    @GetMapping("/t")
    public String test(){
        return "Hello";
    }

}
