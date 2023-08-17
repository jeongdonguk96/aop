package hello.aop.pointcut;

import hello.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method method;

    @BeforeEach
    void init() throws NoSuchMethodException {
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // public java.lang.String hello.member.MemberServiceImpl.hello(java.lang.String)
        log.info("method = {}", method);
    }

    @Test
    void exactMatch1() {
        pointcut.setExpression("execution(public String hello.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void exactMatch2() {
        pointcut.setExpression("execution(public String hello.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void requiredMatch() {
        pointcut.setExpression("execution(String hello(String))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch1() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch2() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch3() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch4() {
        pointcut.setExpression("execution(* hoho(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatch1() {
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatch2() {
        pointcut.setExpression("execution(* hello.aop..*..*.*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeMatch1() {
        pointcut.setExpression("execution(* hello.member.MemberService.*(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatch2() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.member.MemberService.*(..))");
        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internal, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터가 String
    @Test
    void argsMatch1() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 없음
    @Test
    void argsMatch2() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터가 1개이고 어느 타입이든 가능
    @Test
    void argsMatch3() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 유무나 갯수가 상관 없음
    @Test
    void argsMatch4() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }

    // 파라미터 중 첫 번째가 String 고정, 이후는 유무나 갯수가 상관 없음
    @Test
    void argsMatch5() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(method, MemberServiceImpl.class)).isTrue();
    }
}
