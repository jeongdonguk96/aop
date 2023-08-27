package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallService4 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal();
    }
}
