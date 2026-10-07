package org.example.stepwave.service;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Пароль хешируется ровно здесь, один раз. Caller передаёт открытый пароль.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Профиль: меняем только логин. Пароль меняется через changePassword,
     * иначе клиент, приславший объект с уже хешем, получал бы двойное хеширование.
     */
    public User updateProfile(Long id, String username) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        User user = existing.get();
        if (username != null && !username.isBlank()) {
            user.setUsername(username.trim());
        }
        return userRepository.save(user);
    }

    public User changePassword(Long id, String rawPassword) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        User user = existing.get();
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
