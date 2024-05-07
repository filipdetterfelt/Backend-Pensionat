package com.example.backendpensionat.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Blacklist {
    @Id
    @GeneratedValue
    public Long id;

    public Long externalID;
    public String email;
    public String name;
    public String group;
    public Date created;
    public boolean ok;

}
