package de.telran.businesstracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectUiController {

    @RequestMapping(value = {
            "/products/**"})
    public String toRedirect() {
        return "forward:/";
    }
}
