package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CallService2 {

    private CallService2 callService2;

    @Autowired
    public void setCallService2(CallService2 callService2) {
        this.callService2 = callService2;
    }

    public void external() {
        log.info("call external");
        callService2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
