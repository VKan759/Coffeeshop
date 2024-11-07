package kz.onetech.onetechproject.userdetails;

import kz.onetech.onetechproject.model.Person;
import kz.onetech.onetechproject.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация интерфейса {@link UserDetailsService} для загрузки данных пользователя по имени пользователя.
 * Используется для аутентификации и авторизации пользователя с помощью Spring Security.
 *
 * Этот сервис взаимодействует с репозиторием {@link PersonRepository} для поиска пользователя в базе данных
 * по его имени (username). Если пользователь не найден, выбрасывается исключение {@link UsernameNotFoundException}.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    PersonRepository personRepository;

    /**
     * Загружает данные пользователя по имени пользователя.
     *
     * @param username имя пользователя, по которому будет выполнен поиск
     * @return {@link UserDetails} с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(person);
    }
}