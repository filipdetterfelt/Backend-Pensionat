package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Öa-ö]+")
    private String firstName;
    @Pattern(regexp = "[A-Öa-ö]+")
    @Size(min = 2, max = 50)
    private String lastName;
    @Email
    private String email;
    private String phone;
    private String Ssn;

    @OneToMany
    List<Booking> bookings;

}
