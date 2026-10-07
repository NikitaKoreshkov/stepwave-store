package org.example.stepwave.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "reset_token")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String resetToken;  // Столбец для токена сброса

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;  // Столбец для срока действия токена

    @Column(name = "confirmation_code", length = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmationCode;  // Столбец для кода подтверждения

    @Column(name = "confirmation_code_expiry")
    private LocalDateTime confirmationCodeExpiry;  // Новый столбец для срока действия кода подтверждения

    // Конструктор без параметров
    public User() {
    }

    // Конструктор с параметрами
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public LocalDateTime getConfirmationCodeExpiry() {
        return confirmationCodeExpiry;
    }

    public void setConfirmationCodeExpiry(LocalDateTime confirmationCodeExpiry) {
        this.confirmationCodeExpiry = confirmationCodeExpiry;
    }

    // Методы для автоматического заполнения временного поля
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}