package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @GetMapping("/rooms/search")
    public String searchRooms(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
            @RequestParam(name = "amountOfPeople") int amountOfPeople,
            RedirectAttributes model
    ) {
        startDate = startDate == null? LocalDate.now(): startDate;
        endDate = endDate == null? LocalDate.now(): endDate;

        List<RoomDetailedDTO> filteredRooms = roomService.listFreeRooms(startDate, endDate, amountOfPeople);
        model.addFlashAttribute("roomsList", filteredRooms);
        return "redirect:/rooms";
    }

}
