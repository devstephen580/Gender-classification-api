package com.devstephen.gender.classification.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
  private String name;
  private String gender;
  private Double probability;

  @JsonProperty("sample_size")
  private Integer sampleSize;

  @JsonProperty("is_confident")
  private Boolean isConfident;

  @JsonProperty("processed_at")
  private String processedAt;

}
