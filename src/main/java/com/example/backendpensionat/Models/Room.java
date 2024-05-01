package com.example.backendpensionat.Models;

import com.example.backendpensionat.Enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    private Long roomNumber;

   // @Pattern(regexp = "[0-9.]+", message = "Price can only include digits and dots")
    private Double price;
    //@Pattern(regexp = "[0-9]+", message = "size can only include digits")
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
