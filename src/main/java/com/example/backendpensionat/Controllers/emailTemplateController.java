package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.EmailTemplateDTO;
import com.example.backendpensionat.Repos.EmailTemplateRepo;
import com.example.backendpensionat.Services.Impl.EmailTemplateServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class emailTemplateController {
    EmailTemplateRepo emailTemplateRepo;
    EmailTemplateServiceIMPL emailTemplateServiceIMPL;

    @GetMapping("/emailTemplate")
    public String emailTemplate() {
        EmailTemplateDTO emailTemplateDTO = emailTemplateServiceIMPL.emailTemplateToDTO(emailTemplateRepo.findById(1L).get());
        return "emailTemplate";
    }

}
