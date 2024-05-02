package com.example.backendpensionat.DTO;

import com.example.backendpensionat.Enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.util.collections.ReadOnlyMap;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String roomType;

    public RoomType getRoomType() {
        return RoomType.getRoomTypeByString(roomType);
    }
}
