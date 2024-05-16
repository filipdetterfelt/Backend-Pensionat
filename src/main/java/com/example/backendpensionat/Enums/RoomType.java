package com.example.backendpensionat.Enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE( 0, 150.0),
    DOUBLE( 1, 250.0),
    SUITE( 2, 350.0);

    public final int extraBeds;
    private final double roomTypePrice;

    RoomType(int extraBeds, double roomTypePrice) {
        this.extraBeds = extraBeds;
        this.roomTypePrice = roomTypePrice;
    }

    public static RoomType getRoomType(int beds) {
        return switch (beds) {
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.SUITE;
            default -> RoomType.SINGLE;
        };
    }

    public static RoomType getRoomTypeByString(String type) {
        return switch(type) {
            case "DOUBLE" -> RoomType.DOUBLE;
            case "SUITE" -> RoomType.SUITE;
            default -> RoomType.SINGLE;
        };
    }
}