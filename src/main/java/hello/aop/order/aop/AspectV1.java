package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint.getSignature(): 조인포인트의 정보를 가져온다.
        log.info("[log] {}", joinPoint.getSignature());

        // joinPoint.proceed(): 조인포인트를 실행한다.
        // Object 타입을 반환하는데, 조인포인트의 반환 타입이 뭐가 될지 모르기 때문이다.
        return joinPoint.proceed();
    }
}
