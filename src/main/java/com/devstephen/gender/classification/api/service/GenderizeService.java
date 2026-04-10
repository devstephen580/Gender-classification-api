package com.devstephen.gender.classification.api.service;

import com.devstephen.gender.classification.api.dtos.CustomResponse;
import com.devstephen.gender.classification.api.dtos.GenderizeResponse;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenderizeService {


  private final RestTemplate restTemplate = new RestTemplate();
  private static final String GENDERIZE_URL = "https://api.genderize.io/?name=";

  public ResponseEntity<?> classifyName(String name) {
    try {
      // Call Genderize API
      String url = GENDERIZE_URL + name;
      Map response = restTemplate.getForObject(url, Map.class);

      // Edge case: null gender or 0 count
      if (response.get("gender") == null ||
          response.get("count") == null ||
          (Integer) response.get("count") == 0) {
        return ResponseEntity.status(422).body(
            CustomResponse.builder()
                .status("error")
                .message("No prediction available for the provided name")
                .build()
        );
      }

      // Extract fields
      String gender = (String) response.get("gender");
      Double probability = ((Double) response.get("probability"));
      Integer sampleSize = ((Integer) response.get("count"));

      // Compute is_confident
      boolean isConfident = probability >= 0.7 && sampleSize >= 100;

      // Generate processed_at
      String processedAt = Instant.now().toString();

      GenderizeResponse data = GenderizeResponse.builder()
          .name(name)
          .gender(gender)
          .probability(probability)
          .sample_size(sampleSize)
          .is_confident(isConfident)
          .processed_at(processedAt)
          .build();

      return ResponseEntity.ok(
          CustomResponse.builder()
              .status("success")
              .data(data)
              .build()
      );

    } catch (Exception e) {
      return ResponseEntity.status(502).body(
          CustomResponse.builder()
              .status("error")
              .message("Upstream or server failure")
              .build()
      );
    }
  }
}
