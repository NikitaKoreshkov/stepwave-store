package org.example.stepwave.controller;

import org.example.stepwave.model.User;
import org.example.stepwave.service.AuthService;
import org.example.stepwave.web.AuthInterceptor;
import org.example.stepwave.web.SessionPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> authenticated = authService.authenticate(username, password);
        if (authenticated.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        User user = authenticated.get();
        request.getSession(true).setAttribute(AuthInterceptor.SESSION_USER, new SessionPrincipal(user.getId(), user.getUsername()));
        return ResponseEntity.ok("Login successful!");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object principal = session.getAttribute(AuthInterceptor.SESSION_USER);
        if (principal instanceof SessionPrincipal current) {
            return ResponseEntity.ok(current);
        }
        return ResponseEntity.status(401).body("{\"error\": \"Не авторизован\"}");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}
