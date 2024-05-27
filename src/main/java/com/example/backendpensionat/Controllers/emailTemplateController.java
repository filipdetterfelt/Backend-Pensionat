package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.EmailTemplateDTO;
import com.example.backendpensionat.Repos.EmailTemplateRepo;
import com.example.backendpensionat.Services.Impl.EmailTemplateServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class emailTemplateController {
    EmailTemplateRepo emailTemplateRepo;
    EmailTemplateServiceIMPL emailTemplateServiceIMPL;

    @GetMapping("/emailTemplate")
    public String emailTemplate(Model model) {
        EmailTemplateDTO emailTemplateDTO = emailTemplateServiceIMPL.emailTemplateToDTO(emailTemplateRepo.findById(1L).get());
        model.addAttribute("emailTemplate", emailTemplateDTO);
        return "emailTemplate";
    }

    @PostMapping("/emailTemplate")
    public String emailTemplate(EmailTemplateDTO emailTemplateDTO, RedirectAttributes rda) {
        rda.addFlashAttribute("wasChanged", true);
        emailTemplateRepo.save(emailTemplateServiceIMPL.DTOtoEmailTemplate(emailTemplateDTO));
        return "redirect:/emailTemplate";
    }
}
