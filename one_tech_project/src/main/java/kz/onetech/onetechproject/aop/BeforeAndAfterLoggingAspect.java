package kz.onetech.onetechproject.aop;

import kz.onetech.onetechproject.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Slf4j
@Component
public class BeforeAndAfterLoggingAspect {
    @Before("execution(* kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB.findCoffeeByName(..))")
    public void logBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("Вызван метод {}", signature.toShortString());
    }

    @AfterReturning("execution(* kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB.findCoffeeByName(..))")
    public void logAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info("Завершен метод {}", signature.toShortString());
    }

    @Around("execution( * kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB.getCoffeeByName(..))")
    public Optional<Coffee> handleException(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return (Optional<Coffee>) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("Ошибка при поиске кофе");
            return Optional.empty();
        }

    }
}
