package org.example.stepwave.repository;

import org.example.stepwave.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Поиск пользователя по имени
    Optional<User> findByUsername(String username);

    // Проверка на занятость имени пользователя
    boolean existsByUsername(String username);

    // Поиск пользователя по токену сброса пароля
    Optional<User> findByResetToken(String resetToken);

    // Проверка на существование токена сброса пароля
    boolean existsByResetToken(String resetToken);

    // Поиск пользователя по коду подтверждения
    Optional<User> findByConfirmationCode(String confirmationCode);
}