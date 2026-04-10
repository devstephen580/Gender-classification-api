package com.devstephen.gender.classification.api.controller;

import static com.devstephen.gender.classification.api.util.AppConstant.classifyEndpoint;
import static com.devstephen.gender.classification.api.util.AppConstant.controllerMapping;

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
public class ClassifyController {

  private final GenderizeService service;

  @GetMapping(classifyEndpoint)
  public ResponseEntity<?> fetchGenderizer (@RequestParam String name){
    
  }

}
