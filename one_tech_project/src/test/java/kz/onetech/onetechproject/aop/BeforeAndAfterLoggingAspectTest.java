package kz.onetech.onetechproject.aop;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BeforeAndAfterLoggingAspectTest {

    @Mock
    private CoffeeRepositoryDB coffeeRepositoryDB;

    @InjectMocks
    private BeforeAndAfterLoggingAspect aspect;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogBeforeAndAfter() {
        String coffeeName = "Espresso";
        Coffee coffee = new Coffee();
        coffee.setName(coffeeName);
        when(coffeeRepositoryDB.findCoffeeByName(coffeeName)).thenReturn(Optional.of(coffee));

        Optional<Coffee> result = coffeeRepositoryDB.findCoffeeByName(coffeeName);

        verify(coffeeRepositoryDB, times(1)).findCoffeeByName(coffeeName);
        assert result.isPresent();
        assert result.get().getName().equals(coffeeName);
    }
}
