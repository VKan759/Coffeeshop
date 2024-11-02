package kz.onetech.onetechproject.userdetails;

import kz.onetech.onetechproject.model.ERole;
import kz.onetech.onetechproject.model.Person;
import kz.onetech.onetechproject.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;
    private Person person;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setName(ERole.ROLE_USER); // предполагается, что у Role есть перечисление RoleName

        person = Mockito.mock(Person.class);
        Mockito.when(person.getId()).thenReturn(1L);
        Mockito.when(person.getUsername()).thenReturn("testUser");
        Mockito.when(person.getEmail()).thenReturn("test@example.com");
        Mockito.when(person.getPassword()).thenReturn("testPassword");
        Mockito.when(person.getRoles()).thenReturn((Set.of(role)));

        userDetails = UserDetailsImpl.build(person);
    }

    @Test
    void testGetId() {
        assertEquals(1L, userDetails.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void testGetEmail() {
        assertEquals("test@example.com", userDetails.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl anotherUserDetails = UserDetailsImpl.build(person);
        assertEquals(userDetails, anotherUserDetails);
    }

    @Test
    void testHashCode() {
        assertNotNull(userDetails.hashCode());
    }
}
