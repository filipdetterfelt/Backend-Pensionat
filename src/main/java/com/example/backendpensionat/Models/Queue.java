package com.example.backendpensionat.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomIdCSV(String roomIdCSV) {
        this.roomIdCSV = roomIdCSV;
    }

    @Column(name="Name")
    private String name;

    @Column(name="RoomIdCSV")
    private String roomIdCSV;

    @Column(name="MessagesToSend")
    private int messagesToSend;


    public void setMessagesToSend(int messagesToSend) {
        this.messagesToSend = messagesToSend;
    }
}
