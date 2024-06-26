package com.example.backendpensionat.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractCustomer {
    @Id
    @GeneratedValue
    public Long id;

    public Long externalId;
    public String companyName;
    public String contactName;
    public String contactTitle;
    public String streetAddress;
    public String city;
    public Integer postalCode;
    public String country;
    public String phone;
    public String fax;
}
