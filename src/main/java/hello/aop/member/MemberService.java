package hello.aop.member;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    String hello(String param);
}
