package com.devstephen.gender.classification.api.service;

import com.devstephen.gender.classification.api.dtos.CustomResponse;
import com.devstephen.gender.classification.api.dtos.GenderizeResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GenderizeService {

  private final WebClient webClient;

  public GenderizeService(WebClient webClient) {
    this.webClient = webClient;
  }


  public ResponseEntity<?> classifyName(String name) {
    try {

      Map response = webClient.get()
          .uri("/?name=" + name)
          .retrieve()
          .bodyToMono(Map.class)
          .block();

      if (response.get("gender") == null ||
          response.get("count") == null ||
          ((Number) response.get("count")).intValue() == 0) {
        return ResponseEntity.status(422).body(
            GenderizeResponse.builder()
                .status("error")
                .message("No prediction available for the provided name")
                .build()
        );
      }

      String gender = (String) response.get("gender");
      Integer sampleSize = ((Number) response.get("count")).intValue();
      Double probability = ((Number) response.get("probability")).doubleValue();

      boolean isConfident = probability >= 0.7 && sampleSize >= 100;

      String processedAt = Instant.now()
          .truncatedTo(ChronoUnit.SECONDS)
          .toString();

      CustomResponse data = CustomResponse.builder()
          .name(name)
          .gender(gender)
          .probability(probability)
          .sampleSize(sampleSize)
          .isConfident(isConfident)
          .processedAt(processedAt)
          .build();

      return ResponseEntity.ok(
          GenderizeResponse.builder()
              .status("success")
              .data(data)
              .build()
      );

    } catch (Exception e) {
      return ResponseEntity.status(502).body(
          GenderizeResponse.builder()
              .status("error")
              .message("Upstream or server failure")
              .build()
      );
    }
  }
}
