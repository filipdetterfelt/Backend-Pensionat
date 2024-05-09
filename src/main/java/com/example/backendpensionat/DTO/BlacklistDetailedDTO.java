package com.example.backendpensionat.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistDetailedDTO {
   public String statusText;
   public boolean ok;
   public String email;
}
