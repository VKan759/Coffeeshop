package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.dtos.RegisterDTO;
import kz.onetech.onetechproject.dtos.SignupRequestDTO;
import kz.onetech.onetechproject.model.ERole;
import kz.onetech.onetechproject.model.Person;
import kz.onetech.onetechproject.model.Role;
import kz.onetech.onetechproject.repository.PersonRepository;
import kz.onetech.onetechproject.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpShouldReturnBadRequestWhenUsernameExists() {
        SignupRequestDTO request = new SignupRequestDTO("existingUser", "user@example.com", Set.of("user"), "password123");
        when(personRepository.existsByUsername(anyString())).thenReturn(true);

        RegisterDTO response = personService.signUp(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Error: Username or Email is already taken!", response.getMessage());
    }

    @Test
    void signUpShouldReturnBadRequestWhenEmailExists() {
        SignupRequestDTO request = new SignupRequestDTO("newUser", "existing@example.com", Set.of("user"), "password123");
        when(personRepository.existsByEmail(anyString())).thenReturn(true);

        RegisterDTO response = personService.signUp(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("Error: Username or Email is already taken!", response.getMessage());
    }

    @Test
    void signUpShouldAssignUserRoleWhenRoleIsNotSpecified() {
        SignupRequestDTO request = new SignupRequestDTO("newUser", "new@example.com", null, "password123");
        Role userRole = new Role(ERole.ROLE_USER);

        when(personRepository.existsByUsername(anyString())).thenReturn(false);
        when(personRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(encoder.encode(anyString())).thenReturn("encodedPassword");

        RegisterDTO response = personService.signUp(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Person registered successfully!", response.getMessage());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void signUpShouldAssignRolesCorrectlyWhenRolesAreSpecified() {
        SignupRequestDTO request = new SignupRequestDTO("newAdmin", "admin@example.com", Set.of("admin", "mod"), "password123");
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role modRole = new Role(ERole.ROLE_MODERATOR);

        when(personRepository.existsByUsername(anyString())).thenReturn(false);
        when(personRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        when(roleRepository.findByName(ERole.ROLE_MODERATOR)).thenReturn(Optional.of(modRole));
        when(encoder.encode(anyString())).thenReturn("encodedPassword");

        RegisterDTO response = personService.signUp(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Person registered successfully!", response.getMessage());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void signUpShouldThrowExceptionWhenRoleIsNotFound() {
        SignupRequestDTO request = new SignupRequestDTO("newUser", "user@example.com", Set.of("invalidRole"), "password123");
        when(roleRepository.findByName(any(ERole.class))).thenReturn(Optional.empty());

        try {
            personService.signUp(request);
        } catch (RuntimeException ex) {
            assertEquals("Error: Role is not found.", ex.getMessage());
        }
    }

}
