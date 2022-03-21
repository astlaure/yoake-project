package org.astlaure.yoake.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.ServletException;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
public class SecurityLoginFailureHandlerTests {
    private SecurityLoginFailureHandler failureHandler;

    @BeforeEach
    void beforeEach() {
        failureHandler = new SecurityLoginFailureHandler();
    }

    @Test
    void shouldReturnStatus403() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        failureHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("mock data"));

        Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }
}
