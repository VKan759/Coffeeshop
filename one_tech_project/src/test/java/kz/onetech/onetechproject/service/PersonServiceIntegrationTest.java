package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.dtos.RegisterDTO;
import kz.onetech.onetechproject.dtos.SignupRequestDTO;
import kz.onetech.onetechproject.model.Person;
import kz.onetech.onetechproject.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testSignUpIntegration() {
        SignupRequestDTO request = new SignupRequestDTO("newUser", "new@example.com", null, "password123");
        RegisterDTO response = personService.signUp(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Person registered successfully!", response.getMessage());

        Optional<Person> person = personRepository.findByUsername("newUser");
        assertThat(person).isPresent();
    }
}
