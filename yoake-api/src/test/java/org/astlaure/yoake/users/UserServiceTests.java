package org.astlaure.yoake.users;

import org.assertj.core.api.Assertions;
import org.astlaure.yoake.mocks.UserMock;
import org.astlaure.yoake.users.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
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
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnAnExistingUser() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(UserMock.getInstance()));

        UserDetails user = userService.loadUserByUsername("jasmine@karma.io");

        Assertions.assertThat(user.getPassword()).isEqualTo("encrypted");
    }

    @Test
    void shouldThrowAnException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.loadUserByUsername("jasmine@karma.io"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
