package com.devstephen.gender.classification.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
  private Integer sampleSize;
  private Boolean isConfident;
  private String processedAt;

}
