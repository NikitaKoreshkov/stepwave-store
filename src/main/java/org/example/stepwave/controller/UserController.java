package org.example.stepwave.controller;

import org.example.stepwave.model.User;
import org.example.stepwave.service.UserService;
import org.example.stepwave.web.AuthInterceptor;
import org.example.stepwave.web.SessionPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String PASSWORD_RULE =
            "Пароль должен быть не менее 8 символов и содержать хотя бы одну заглавную букву.";

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private static boolean isWeakPassword(String password) {
        return password == null || password.length() < 8 || !password.matches(".*[A-Z].*");
    }

    /** Регистрация: открытый пароль приходит сюда, хеш ставит сервис, один раз. */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return badRequest("Имя пользователя не может быть пустым.");
        }
        if (isWeakPassword(user.getPassword())) {
            return badRequest(PASSWORD_RULE);
        }
        if (userService.isUsernameTaken(user.getUsername())) {
            return badRequest("Извините, данное имя пользователя уже занято.");
        }

        userService.createUser(user);
        return ResponseEntity.ok("{\"message\": \"Вы успешно создали аккаунт, пожалуйста войдите в систему.\"}");
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<String> checkUsername(@PathVariable String username) {
        boolean taken = userService.isUsernameTaken(username);
        return ResponseEntity.ok(taken ? "{\"exists\": true}" : "{\"exists\": false}");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, HttpSession session) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return notFound();
        }
        if (!owns(user.get(), session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\": \"Нет доступа к этому аккаунту\"}");
        }
        return ResponseEntity.ok(user.get());
    }

    /** Профиль: логин. Пароль здесь не принимается, для него отдельный эндпоинт. */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User payload, HttpSession session) {
        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) {
            return notFound();
        }
        if (!owns(existing.get(), session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\": \"Нет доступа к этому аккаунту\"}");
        }
        String username = payload.getUsername();
        if (username != null && username.isBlank()) {
            return badRequest("Имя пользователя не может быть пустым.");
        }
        if (username != null && !username.equals(existing.get().getUsername())
                && userService.isUsernameTaken(username)) {
            return badRequest("Извините, данное имя пользователя уже занято.");
        }
        return ResponseEntity.ok(userService.updateProfile(id, username));
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                            @RequestBody Map<String, String> body,
                                            HttpSession session) {
        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) {
            return notFound();
        }
        if (!owns(existing.get(), session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\": \"Нет доступа к этому аккаунту\"}");
        }

        String current = body.get("currentPassword");
        String next = body.get("newPassword");
        if (current == null || !passwordEncoder.matches(current, existing.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Текущий пароль неверный\"}");
        }
        if (isWeakPassword(next)) {
            return badRequest(PASSWORD_RULE);
        }

        userService.changePassword(id, next);
        return ResponseEntity.ok("{\"message\": \"Пароль обновлён\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, HttpSession session) {
        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) {
            return notFound();
        }
        if (!owns(existing.get(), session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\": \"Нет доступа к этому аккаунту\"}");
        }
        userService.deleteUser(id);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    private static boolean owns(User user, HttpSession session) {
        Object principal = session.getAttribute(AuthInterceptor.SESSION_USER);
        return principal instanceof SessionPrincipal current
                && current.id().equals(user.getId());
    }

    private static ResponseEntity<String> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Пользователь не найден.\"}");
    }

    private static ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest().body("{\"error\": \"" + message + "\"}");
    }
}
