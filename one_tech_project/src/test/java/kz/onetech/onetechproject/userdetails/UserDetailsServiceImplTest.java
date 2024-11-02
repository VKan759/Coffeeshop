package kz.onetech.onetechproject.userdetails;

import kz.onetech.onetechproject.model.Person;
import kz.onetech.onetechproject.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setUsername("testUser");
        person.setEmail("test@example.com");
        person.setPassword("password");
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        when(personRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(person));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        verify(personRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(personRepository.findByUsername(anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonExistentUser"));

        verify(personRepository, times(1)).findByUsername("nonExistentUser");
    }
}
