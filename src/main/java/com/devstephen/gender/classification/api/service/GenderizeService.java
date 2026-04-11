package com.devstephen.gender.classification.api.service;

import com.devstephen.gender.classification.api.dtos.CustomResponse;
import com.devstephen.gender.classification.api.dtos.GenderizeResponse;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GenderizeService {


  private static final String GENDERIZE_URL = "https://api.genderize.io/?name=";
  private final WebClient webClient;

  public GenderizeService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl(GENDERIZE_URL).build();
  }
  
  public ResponseEntity<?> classifyName(String name) {
    try {

      Map response = webClient.get()
          .uri("/?name=" + name)
          .retrieve()
          .bodyToMono(Map.class)
          .block(); // block() makes it synchronous like RestTemplate

      // Edge case: null gender or 0 count
      if (response.get("gender") == null ||
          response.get("count") == null ||
          (Integer) response.get("count") == 0) {
        return ResponseEntity.status(422).body(
            GenderizeResponse.builder()
                .status("error")
                .message("No prediction available for the provided name")
                .build()
        );
      }

      String gender = (String) response.get("gender");
      Double probability = ((Double) response.get("probability"));
      Integer sampleSize = ((Integer) response.get("count"));

      boolean isConfident = probability >= 0.7 && sampleSize >= 100;

      String processedAt = Instant.now().toString();

      CustomResponse data = CustomResponse.builder()
          .name(name)
          .gender(gender)
          .probability(probability)
          .sample_size(sampleSize)
          .is_confident(isConfident)
          .processed_at(processedAt)
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
