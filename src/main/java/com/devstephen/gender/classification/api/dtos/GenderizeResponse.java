package com.devstephen.gender.classification.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenderizeResponse {
  private String status;
  private Object data;
  private String message;
}
