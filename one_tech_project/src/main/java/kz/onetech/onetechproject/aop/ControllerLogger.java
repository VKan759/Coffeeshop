package kz.onetech.onetechproject.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class ControllerLogger {

    @Around("execution(* kz.onetech.onetechproject.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";

        log.info("Incoming request: {} {} from IP: {} by user: {}", method, url, ipAddress, username);
        Object result = joinPoint.proceed();
        log.info("Outgoing response: {}", result);

        return result;
    }

}
