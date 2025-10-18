package com.jurtikom.ftime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jurtikom.ftime.model.Greeting;

@RestController
@RequestMapping("/api")
public class HelloController {

  private List<Greeting> greetings = new ArrayList<Greeting>();
  
  public HelloController() {
    greetings.add(new Greeting(1L, "Hello, Spring!"));
    greetings.add(new Greeting(2L, "Hello, World!"));
    greetings.add(new Greeting(3L, "Hello, REST!"));
  }

  @GetMapping("/hello")
  public List<Greeting> getAllGreetings(@RequestParam(defaultValue="") String message) {
    if (message.isEmpty()) {
      return greetings;
    }

    return greetings.stream().filter(value -> value.getMessage().contains(message)).collect(Collectors.toList());
  }

  @GetMapping("/hello/{id}")
  public Greeting getGreetingById(@PathVariable Long id) {
    return greetings.stream().filter(greeting -> greeting.getId().equals(id)).findFirst().orElse(null);
  }

  @PostMapping("/hello")
  @ResponseStatus(HttpStatus.CREATED)
  public Greeting createGreeting(@RequestBody Greeting greeting) {
    greeting.setId((long) (greetings.size()+1));
    greetings.add(greeting);
    return greeting;
  }

  @PutMapping("/hello/{id}")
  public Greeting updateGreeting(@PathVariable Long id, @RequestBody Greeting greeting) {
    Greeting existingGreeting = greetings.stream().filter(greet -> greet.getId().equals(id)).findFirst().orElse(null);

    if (existingGreeting != null) {
      existingGreeting.setMessage(greeting.getMessage());
    }

    return existingGreeting;
  }

  @DeleteMapping("/hello/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteGreeting(@PathVariable long id) {
    greetings.removeIf(value -> value.getId().equals(id));
  }
}
