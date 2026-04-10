package com.devstephen.gender.classification.api.controller;

import static com.devstephen.gender.classification.api.util.AppConstant.controllerMapping;

import com.devstephen.gender.classification.api.service.GenderizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(controllerMapping)
@RequiredArgsConstructor
public class ClassifyController {

  private static final GenderizeService service;

}
