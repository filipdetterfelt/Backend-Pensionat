package com.example.backendpensionat.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "customers")
public class ContractCustomerDTO {
    public Long id;
    @JacksonXmlProperty(localName = "id")
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
