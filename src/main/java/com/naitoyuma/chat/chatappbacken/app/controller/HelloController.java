package com.naitoyuma.chat.chatappbacken.app.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naitoyuma.chat.chatappbacken.domain.hello.model.Hello;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public Hello hello(@RequestParam("name") Optional<String> name) {
    String resName = name.orElse("world!");
    return new Hello("Hello, " + resName);
  }
}