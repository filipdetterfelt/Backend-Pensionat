package com.example.backendpensionat.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistDTO {
    @JsonProperty("id")
    public Long externalID;
    public String email;
    public String name;
    public String group;
    public String created;
    public boolean ok;
}
