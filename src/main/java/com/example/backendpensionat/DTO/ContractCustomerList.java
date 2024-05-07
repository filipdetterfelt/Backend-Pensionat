package com.example.backendpensionat.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "allcustomers")
public class ContractCustomerList {
    @JacksonXmlProperty(localName = "customers")
    public List<ContractCustomerDTO> contractCustomerDTOList;
}
