package com.devstephen.gender.classification.api.controller;

import static com.devstephen.gender.classification.api.util.AppConstant.classifyEndpoint;
import static com.devstephen.gender.classification.api.util.AppConstant.controllerMapping;

import com.devstephen.gender.classification.api.dtos.GenderizeResponse;
import com.devstephen.gender.classification.api.service.GenderizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(controllerMapping)
@RequiredArgsConstructor
public class Controller {

  private final GenderizeService service;

  @GetMapping(classifyEndpoint)
  public ResponseEntity<?> fetchGenderizer (@RequestParam (required = false) String name){

    if (!name.matches("[a-zA-Z]+")) {
      return ResponseEntity.status(422).body(
          GenderizeResponse.builder()
              .status("error")
              .message("Name must contain only alphabetic characters")
              .build()
      );
    }

    if (name == null || name.trim().isEmpty()) {
      return ResponseEntity.status(400).body(
          GenderizeResponse.builder()
              .status("error")
              .message("Missing or empty name parameter")
              .build()
      );
    }

    return service.classifyName(name);
  }

}
