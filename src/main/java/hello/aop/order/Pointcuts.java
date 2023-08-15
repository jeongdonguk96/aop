package hello.aop.order;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // 패키지와 하위 경로 적용
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {} // 포인트컷 시그니처

    // 클래스 이름이 *Service 적용 (이름 패턴)
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {} // 포인트컷 시그니처

    // 패키지와 클래스명
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {} // 포인트컷 시그니처
}
