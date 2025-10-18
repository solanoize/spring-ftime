package com.jurtikom.ftime.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jurtikom.ftime.service.GreetingService;



@RestController
@RequestMapping("/api")
public class HelloController {

  private final GreetingService greetingService;

  public HelloController(GreetingService greetingService) {
    this.greetingService = greetingService;
  }
  
  @GetMapping("/hello")
  public String sayHello() {
    return "Hello, Jurtikom!";
  }

  @GetMapping("/hello/{name}")
  public String sayHello(@PathVariable String name) {
    return "Hello, " + name + "!";
  }

  @GetMapping("/greet")
  public String greet() {
    return greetingService.getGreeting();
  }
  

  @GetMapping("/greets")
  public Map<String, String> greets() {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Greeting from Srping Boot!");
      response.put("version", "1.0");

      return response;
  }


  
}
