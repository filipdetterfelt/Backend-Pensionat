package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Services.RoomEventService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomEventService roomEventService;

    @GetMapping("/rooms")
    public String allRooms(Model model) {
        model.addAttribute("roomsList", roomService.listAllRooms());
        model.addAttribute("roomSearch", new RoomSearchDTO());
        return "rooms";
    }

    @PostMapping("/rooms")
    public String searchRooms(@ModelAttribute("roomSearch")RoomSearchDTO roomSearch, Model model) {
        List<RoomDetailedDTO> filteredRooms = roomService.listFreeRooms(roomSearch);
        model.addAttribute("roomsList", filteredRooms);
        return "rooms";
    }

   @GetMapping("/roomLog/{id}")
   public String showLogs(@PathVariable Long id, Model model){
        RoomDetailedDTO room = roomService.findRoomById(id);
        Long roomName = room.getRoomNumber();
        model.addAttribute("roomEventList", roomEventService.findRoomByRoomNumber(String.valueOf(room.getRoomNumber())));
        model.addAttribute("roomName", roomName);
       System.out.println(roomEventService.findRoomByRoomNumber(String.valueOf(room.getRoomNumber())));
        return "roomLog";
   }
}
