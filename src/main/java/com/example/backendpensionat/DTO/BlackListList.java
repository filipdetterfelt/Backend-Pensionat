package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Models.Blacklist;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "fullBlacklist")
public class BlackListList {
    @JacksonXmlProperty(localName = "customers")
    List <BlacklistDTO> blacklist;
}
