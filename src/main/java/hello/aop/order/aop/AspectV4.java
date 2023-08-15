package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV4 {

    @Around("hello.aop.order.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint.getSignature(): 조인포인트의 정보를 가져온다.
        log.info("[log] {}", joinPoint.getSignature());

        // joinPoint.proceed(): 조인포인트를 실행한다.
        // Object 타입을 반환하는데, 조인포인트의 반환 타입이 뭐가 될지 모르기 때문이다.
        return joinPoint.proceed();
    }

    // 패키지 포인트컷과 클래스명 포인트컷 둘 다 포함한 곳에 사용
    @Around("hello.aop.order.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
