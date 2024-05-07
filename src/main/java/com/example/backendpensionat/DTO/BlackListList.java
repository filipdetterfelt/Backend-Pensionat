package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Models.Blacklist;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "")
public class BlackListList {
    @JacksonXmlProperty(localName = "")
    List <BlacklistDTO> blacklist;
}
