package org.example.stepwave.service;

import org.example.stepwave.model.User;
import org.example.stepwave.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private final UserRepository repository = mock(UserRepository.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AuthService service = new AuthService(repository, encoder);

    @Test
    void returnsUserForCorrectCredentials() {
        when(repository.findByUsername("shoe_fan"))
                .thenReturn(Optional.of(new User("shoe_fan", encoder.encode("Probe1234"))));

        assertThat(service.authenticate("shoe_fan", "Probe1234")).isPresent();
    }

    @Test
    void rejectsWrongPassword() {
        when(repository.findByUsername("shoe_fan"))
                .thenReturn(Optional.of(new User("shoe_fan", encoder.encode("Probe1234"))));

        assertThat(service.authenticate("shoe_fan", "guessing")).isEmpty();
    }

    @Test
    void rejectsUnknownUserWithoutTouchingTheEncoder() {
        when(repository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThat(service.authenticate("ghost", "Probe1234")).isEmpty();
    }

    @Test
    void rejectsNullInputs() {
        assertThat(service.authenticate(null, null)).isEmpty();
        assertThat(service.authenticate("shoe_fan", null)).isEmpty();
    }
}
