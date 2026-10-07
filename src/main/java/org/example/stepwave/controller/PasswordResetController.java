package org.example.stepwave.controller;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.example.stepwave.service.CodeGenerator;
import org.example.stepwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api/email")
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Метод для запроса сброса пароля (отправляем код подтверждения)
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UsernameRequest usernameRequest) {
        String username = usernameRequest.getUsername();
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Генерация 6-значного кода подтверждения
            String confirmationCode = CodeGenerator.sixDigits();
            user.setConfirmationCode(confirmationCode);  // Сохраняем код в базе данных
            user.setConfirmationCodeExpiry(java.time.LocalDateTime.now().plusMinutes(10)); // Срок действия кода
            userRepository.save(user);

            // Логируем информацию
            logger.info("Сгенерирован код подтверждения для пользователя: {}", username);

            // Отправляем код подтверждения на email
            try {
                emailService.sendConfirmationCodeEmail(user.getUsername(), confirmationCode);
                return ResponseEntity.ok("Код подтверждения отправлен на указанный email.");
            } catch (Exception e) {
                logger.error("Ошибка при отправке письма для пользователя: {}", username, e);
                return ResponseEntity.status(500).body("Ошибка при отправке письма. Попробуйте позже.");
            }
        }

        return ResponseEntity.status(400).body("Пользователь с таким username не найден.");
    }

    // Страница для ввода нового пароля
    @GetMapping("/reset-password-form")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isPresent() && userOpt.get().getResetTokenExpiry().isAfter(java.time.LocalDateTime.now())) {
            model.addAttribute("token", token);
            return "reset-password-form";  // Здесь будет ваш фронт для сброса пароля
        } else {
            model.addAttribute("error", "Срок действия токена истек или токен недействителен.");
            return "error";  // Страница ошибки, если токен неверный или истек
        }
    }

    // Метод для сброса пароля
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String newPassword = resetPasswordRequest.getNewPassword();

        Optional<User> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
                logger.warn("Срок действия токена для пользователя {} истек.", user.getUsername());
                return ResponseEntity.status(400).body("Срок действия токена истек.");
            }

            user.setPassword(passwordEncoder.encode(newPassword)); // Хешируем новый пароль
            user.setResetToken(null); // Убираем токен
            user.setResetTokenExpiry(null); // Убираем срок действия токена
            userRepository.save(user); // Сохраняем обновленного пользователя

            // Логируем успешное обновление пароля
            logger.info("Пароль успешно обновлен для пользователя: {}", user.getUsername());

            return ResponseEntity.ok("Пароль успешно обновлен.");
        }

        return ResponseEntity.status(400).body("Неверный или истекший токен.");
    }

    // Статический DTO для запроса имени пользователя
    public static class UsernameRequest {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    // Статический DTO для сброса пароля
    public static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}