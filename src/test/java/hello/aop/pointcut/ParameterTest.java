package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember(){}

        // 기본 방식, joinPoint에서 매개변수를 가져온다.
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {}, arg= {}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // 지시자에 args를 더하고, 파라미터에 추가해 가져온다.
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg= {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // 매개변수를 가져오는 방법은 2번과 동일하지만, @Before를 사용함으로써 조인포인트를 제외했다.
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(Object arg) throws Throwable {
            log.info("[logArgs3] arg= {}", arg);
        }

        // this를 사용해 프록시 객체의 정보를 가져온다.
        @Before("allMember() && this(obj)")
        public void logArgs4(JoinPoint joinPoint, Object obj) throws Throwable {
            log.info("[this] {}, obj= {}", joinPoint.getSignature(),  obj.getClass());
        }

        // target을 사용해 실제 객체의 정보를 가져온다.
        @Before("allMember() && target(obj)")
        public void logArgs5(JoinPoint joinPoint, Object obj) throws Throwable {
            log.info("[target] {}, obj= {}", joinPoint.getSignature(),  obj.getClass());
        }
    }
}
