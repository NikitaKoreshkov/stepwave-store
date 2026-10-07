package org.example.stepwave.controller;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.example.stepwave.service.CodeGenerator;
import org.example.stepwave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api/email")
public class VerifyCodeController {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Метод для запроса сброса пароля (отправляем код подтверждения)
    @PostMapping("/forgot-password-request")
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

            // Логируем факт выдачи кода, но не сам код
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

    // Метод для отображения страницы для ввода кода подтверждения
    @GetMapping("/verify-code")
    public String showVerificationPage() {
        // Возвращаем имя страницы для отображения формы ввода кода
        return "verify-code"; // Этот шаблон должен быть доступен в папке resources/templates
    }

    // Метод для проверки кода и генерации токена сброса пароля
    @PostMapping("/verify-code/check")
    public ResponseEntity<String> verifyCodeAndGenerateToken(@RequestBody CodeRequest codeRequest) {
        String code = codeRequest.getCode();
        Optional<User> userOpt = userRepository.findByConfirmationCode(code);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Проверяем код и его срок действия
            if (user.getConfirmationCodeExpiry() != null && user.getConfirmationCodeExpiry().isAfter(java.time.LocalDateTime.now())) {
                // Генерация токена для сброса пароля
                String resetToken = UUID.randomUUID().toString();
                user.setResetToken(resetToken);
                user.setResetTokenExpiry(java.time.LocalDateTime.now().plusHours(1));  // Токен действует 1 час
                userRepository.save(user);

                // Удаляем код подтверждения и его время из базы данных
                user.setConfirmationCode(null);
                user.setConfirmationCodeExpiry(null);
                userRepository.save(user);

                // Отправляем токен на email для сброса пароля
                try {
                    emailService.sendPasswordResetToken(user.getUsername(), resetToken);
                    return ResponseEntity.ok("Токен для сброса пароля отправлен на ваш email.");
                } catch (Exception e) {
                    logger.error("Ошибка при отправке токена для сброса пароля для пользователя: {}", user.getUsername(), e);
                    return ResponseEntity.status(500).body("Ошибка при отправке токена. Попробуйте позже.");
                }
            } else {
                return ResponseEntity.status(400).body("Неверный или истекший код подтверждения.");
            }
        }

        return ResponseEntity.status(400).body("Пользователь с таким кодом не найден.");
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

    // Статический DTO для запроса кода подтверждения
    public static class CodeRequest {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}