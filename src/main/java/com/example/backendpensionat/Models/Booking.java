package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue

    private Long Id;
    @Pattern(regexp = "[0-9]+", message = "Amount can only include digits")
    private int amount;
    @Pattern(regexp = "[0-9.]+", message = "TotalPrice can only include digits and dots")
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;


}
