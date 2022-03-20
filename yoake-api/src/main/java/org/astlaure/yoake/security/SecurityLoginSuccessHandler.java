package org.astlaure.yoake.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.astlaure.yoake.auth.models.ProfileDto;
import org.astlaure.yoake.users.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        response.getWriter().write(objectMapper.writeValueAsString(ProfileDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .username(user.getUsername())
                        .role(user.getRole().name())
                .build()));
        response.setStatus(200);
    }
}
