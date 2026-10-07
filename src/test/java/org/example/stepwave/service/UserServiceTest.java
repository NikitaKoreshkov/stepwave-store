package org.example.stepwave.service;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository repository = mock(UserRepository.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService service = new UserService(repository, encoder);

    @Test
    void createUserStoresPasswordHashedExactlyOnce() {
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User saved = service.createUser(new User("shoe_fan", "Probe1234"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        User stored = captor.getValue();

        assertThat(stored.getPassword()).isNotEqualTo("Probe1234");
        assertThat(encoder.matches("Probe1234", stored.getPassword())).isTrue();
        assertThat(saved.getPassword()).isEqualTo(stored.getPassword());
    }

    @Test
    void changePasswordReplacesHashWithOneRound() {
        User existing = new User("shoe_fan", encoder.encode("Probe1234"));
        existing.setId(7L);
        when(repository.findById(7L)).thenReturn(Optional.of(existing));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = service.changePassword(7L, "Another2345");

        assertThat(encoder.matches("Another2345", updated.getPassword())).isTrue();
        assertThat(encoder.matches("Probe1234", updated.getPassword())).isFalse();
    }

    @Test
    void updateProfileLeavesPasswordUntouched() {
        String hash = encoder.encode("Probe1234");
        User existing = new User("shoe_fan", hash);
        existing.setId(7L);
        when(repository.findById(7L)).thenReturn(Optional.of(existing));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = service.updateProfile(7L, "shoe_fan_new");

        assertThat(updated.getUsername()).isEqualTo("shoe_fan_new");
        assertThat(updated.getPassword()).isEqualTo(hash);
    }
}
