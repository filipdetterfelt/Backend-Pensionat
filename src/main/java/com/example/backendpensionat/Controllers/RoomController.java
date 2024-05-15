package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import com.example.backendpensionat.Models.RoomEvents;
import com.example.backendpensionat.Services.RoomService;
import com.rabbitmq.tools.json.JSONUtil;
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
        model.addAttribute("roomEventList", roomService.findRoomByRoomNumber(String.valueOf(room.getRoomNumber())));

       System.out.println(roomService.findRoomByRoomNumber(String.valueOf(room.getRoomNumber())));
        return "roomLog";
   }

   /*@GetMapping("/roomLogs")
    public String showLogs(Model model){
        model.addAttribute("roomEventList", roomService.findAllRoomEvents());
        return "roomLog";
   }*/



    /*@GetMapping("/roomLog/{id}")
    public String showLogs2(@PathVariable Long id, Model model){
        List<EventType> roomEvents = roomService.findRoomEventsById(id);
        model.addAttribute("roomLogList", roomEvents);
        return "roomLog";
    }*/


}
