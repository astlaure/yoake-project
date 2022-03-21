package org.astlaure.yoake.auth;

import org.astlaure.yoake.users.User;
import org.astlaure.yoake.users.UserRepository;
import org.astlaure.yoake.users.enums.UserRole;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .name("Jasmine Karma")
                        .username("jasmine@karma.io")
                        .password("encrypted")
                        .role(UserRole.ROLE_ADMIN)
                        .enabled(true)
                        .build()));
    }

    @Test
    void shouldReturn403WhenUnauthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/auth/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithUserDetails(value = "jasmine@karma.io", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void shouldReturnProfileWhenAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/auth/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("jasmine@karma.io")));
    }
}
