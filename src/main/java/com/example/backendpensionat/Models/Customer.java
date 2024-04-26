package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 50)
   // @Pattern(regexp = "[A-Öa-ö]+", message = "First name can only contain letters")
    private String firstName;
    //@Pattern(regexp = "[A-Öa-ö]+", message = "Last name can only contain letters")
    @Size(min = 2, max = 50)
    private String lastName;
    @Email(message = "Must be an email")
    private String email;
    //@Pattern(regexp = "[\\-0-9 ]+", message = "Phone number can only be numbers & ' - ' & ' / ' ")
    private String phone;
    //@Pattern(regexp = "[0-9 \\-]+", message = "Social security number can only be numbers")
    private String Ssn;

    @OneToMany(mappedBy = "customer")
    List<Booking> bookings;

}
