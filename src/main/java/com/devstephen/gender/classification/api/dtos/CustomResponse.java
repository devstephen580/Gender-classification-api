package com.devstephen.gender.classification.api.dtos;

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
  private Integer sample_size;
  private Boolean is_confident;
  private String processed_at;

}
