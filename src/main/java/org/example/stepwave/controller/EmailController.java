package org.example.stepwave.controller;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.example.stepwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Метод для отправки письма
    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendHtmlEmail(to, subject, text);
        return "Email отправлен на " + to;
    }
}