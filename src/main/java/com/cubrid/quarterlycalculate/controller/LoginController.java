package com.cubrid.quarterlycalculate.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/")
@RestController
public class LoginController {

    @GetMapping("/api/login")
    public String test() {
        return "원종민";
    }
}
