package org.astlaure.yoake.security;

import org.assertj.core.api.Assertions;
import org.astlaure.yoake.mocks.UserMock;
import org.astlaure.yoake.users.User;
import org.astlaure.yoake.users.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.ServletException;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class SecurityLoginSuccessHandlerTests {
    private SecurityLoginSuccessHandler successHandler;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void beforeEach() {
        successHandler = new SecurityLoginSuccessHandler();
        Mockito.when(authentication.getPrincipal()).thenReturn(UserMock.getInstance());
    }

    @Test
    void shouldReturnProfile() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        successHandler.onAuthenticationSuccess(request, response, authentication);

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.getContentAsString()).contains("jasmine@karma.io");
    }
}
