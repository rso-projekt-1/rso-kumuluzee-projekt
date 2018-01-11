package rso.project.customers.api.interceptor;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.cdi.Log;
import org.apache.logging.log4j.CloseableThreadContext;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.UUID;

@Log
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
public class LogInterceptor {

    @AroundInvoke
    public Object logEntryExit(InvocationContext context) throws Exception{
        ConfigurationUtil configurationUtil = ConfigurationUtil.getInstance();
        HashMap settings = new HashMap();
        settings.put("environmentType", configurationUtil.get("kumuluzee.env.name").orElse(null));
        settings.put("applicationName", configurationUtil.get("kumuluzee.name").orElse(null));
        settings.put("applicationVersion", configurationUtil.get("kumuluzee.version").orElse(null));
        settings.put("uniqueInstanceId", EeRuntime.getInstance().getInstanceId());
        settings.put("uniqueRequestId", UUID.randomUUID().toString());

        try(final CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(settings)){
            return context.proceed();
        }

    }
}