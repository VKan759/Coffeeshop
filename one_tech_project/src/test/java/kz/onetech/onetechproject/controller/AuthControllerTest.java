package kz.onetech.onetechproject.controller;

import kz.onetech.onetechproject.dtos.*;
import kz.onetech.onetechproject.jwt.JwtUtils;
import kz.onetech.onetechproject.service.PersonService;
import kz.onetech.onetechproject.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private PersonService personService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_shouldReturnMessageResponseDTO() {
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO();
        RegisterDTO registerDTO = new RegisterDTO(HttpStatus.OK, "User registered successfully");
        when(personService.signUp(signupRequestDTO)).thenReturn(registerDTO);

        ResponseEntity<MessageResponseDTO> response = authController.registerUser(signupRequestDTO);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody().getMessage());
        verify(personService, times(1)).signUp(signupRequestDTO);
    }

    @Test
    void authenticateUser_shouldReturnJwtResponseDTO() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("user", "password");
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "user", "password", "user@example.com", List.of());
        String token = "mockJwtToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(token);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<JwtResponseDTO> response = authController.authenticateUser(loginRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(token, response.getBody().getToken());
        assertEquals("user", response.getBody().getUsername());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }
}
