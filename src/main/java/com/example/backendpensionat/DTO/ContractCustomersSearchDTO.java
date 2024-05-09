package com.example.backendpensionat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCustomersSearchDTO {
    public String orderString;
    public String searchWord;
}

