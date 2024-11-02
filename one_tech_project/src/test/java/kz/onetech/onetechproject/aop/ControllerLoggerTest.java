package kz.onetech.onetechproject.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ControllerLoggerTest {

    private ControllerLogger controllerLogger;
    private ProceedingJoinPoint joinPoint;

    @BeforeEach
    public void setUp() {
        controllerLogger = new ControllerLogger();
        joinPoint = mock(ProceedingJoinPoint.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mock(HttpServletRequest.class)));
    }

    @Test
    void logAroundShouldLogIncomingRequest() throws Throwable {
        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getMethod()).thenReturn("GET");
        when(request.getUserPrincipal()).thenReturn(null); // Simulate anonymous user
        when(joinPoint.proceed()).thenReturn("Response");

        Object response = controllerLogger.logAround(joinPoint);

        assertEquals("Response", response);

        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void logAroundShouldLogOutgoingResponse() throws Throwable {
        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getUserPrincipal()).thenReturn(() -> "testUser"); // Simulate authenticated user
        when(joinPoint.proceed()).thenReturn("Response");

        Object response = controllerLogger.logAround(joinPoint);

        assertEquals("Response", response);

        verify(joinPoint, times(1)).proceed();
    }
}
