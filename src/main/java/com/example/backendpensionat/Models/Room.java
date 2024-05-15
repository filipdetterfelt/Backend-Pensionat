package com.example.backendpensionat.Models;

import com.example.backendpensionat.Enums.RoomType;
import jakarta.persistence.*;
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

    private Double price;
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
