package org.astlaure.yoake.users;

import org.assertj.core.api.Assertions;
import org.astlaure.yoake.users.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldReturnAnExistingUser() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(User.builder()
                                .id(1L)
                                .name("Jasmine Karma")
                                .username("jasmine@karma.io")
                                .password("encrypted")
                                .role(UserRole.ROLE_ADMIN)
                                .enabled(true)
                        .build()));

        UserService userService = new UserService(userRepository);

        UserDetails user = userService.loadUserByUsername("jasmine@karma.io");

        Assertions.assertThat(user.getPassword()).isEqualTo("encrypted");
    }

    @Test
    void shouldThrowAnException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        UserService userService = new UserService(userRepository);

        Assertions.assertThatThrownBy(() -> userService.loadUserByUsername("jasmine@karma.io"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
