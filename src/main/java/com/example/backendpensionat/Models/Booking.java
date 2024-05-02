package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private Long Id;
  //  @Pattern(regexp = "[0-9]+", message = "Amount can only include digits")
    private int amountOfBeds;
   // @Pattern(regexp = "[0-9.]+", message = "TotalPrice can only include digits and dots")
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Room room;


}
