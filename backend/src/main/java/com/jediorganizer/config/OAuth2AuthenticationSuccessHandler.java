package com.jediorganizer.config;

import com.jediorganizer.model.User;
import com.jediorganizer.service.JwtService;
import com.jediorganizer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2 Authentication Success Handler to generate JWT tokens after successful OAuth2 login.
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserService userService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public OAuth2AuthenticationSuccessHandler(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Extract user information from OAuth2User
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");
        String picture = oAuth2User.getAttribute("picture");

        User user = userService.createOrUpdateOAuthUser(email, name, googleId, picture);

        String jwt = jwtService.generateToken(user.getEmail());

        // Redirect to frontend with JWT token
        String redirectUrl = String.format("%s/auth/callback?token=%s", frontendUrl, jwt);
        response.sendRedirect(redirectUrl);
    }
}
