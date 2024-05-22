package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @Column(name="Name")
    private String name;

    @Column(name="RoomIdCSV")
    private String roomIdCSV;

    @Column(name="MessagesToSend")
    private int messagesToSend;


}
