package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CallService3 {

//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallService3> callService3ObjectProvider;

    public CallService3(ObjectProvider<CallService3> callService3ObjectProvider) {
        this.callService3ObjectProvider = callService3ObjectProvider;
    }

    public void external() {
        log.info("call external");
        CallService3 callService3 =
                callService3ObjectProvider.getObject();
        callService3.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
