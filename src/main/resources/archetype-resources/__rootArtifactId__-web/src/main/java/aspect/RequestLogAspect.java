package ${package}.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestLogAspect
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/09 11:08
 */
@Component
@Aspect
public class RequestLogAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestLogAspect.class);

    @Pointcut("execution(* your_package..*(..))")
    public void requestServer() {
    }

    @Before("requestServer()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LOGGER.info("===============================Start========================");
        LOGGER.info("IP                 : {}", request.getRemoteAddr());
        LOGGER.info("URL                : {}", request.getRequestURL().toString());
        LOGGER.info("HTTP Method        : {}", request.getMethod());
        LOGGER.info("Class Method       : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        LOGGER.info("Request Args       : {}", joinPoint.getArgs());
    }


    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        LOGGER.info("Response Args        : {}", result);
        LOGGER.info("Time Cost            : {} ms", System.currentTimeMillis() - start);

        return result;
    }

    @After("requestServer()")
    public void doAfter(JoinPoint joinPoint) {
        LOGGER.info("===============================End========================");
    }
}
