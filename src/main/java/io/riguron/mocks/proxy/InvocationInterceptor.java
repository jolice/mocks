package io.riguron.mocks.proxy;

import io.riguron.mocks.invocation.Invocation;

import java.util.HashMap;
import java.util.Map;

public final class InvocationInterceptor {

    private Map<Object, Interceptor> actions = new HashMap<>();

    public void interceptNextCall(Object proxy, Interceptor interceptor) {
        this.actions.put(proxy, interceptor);
    }

    public boolean handleCall(Invocation invocation) {
        boolean result = false;
        Interceptor interceptor = actions.get(invocation.getProxy());
        if (interceptor != null) {
            interceptor.interceptCall(invocation);
            actions.remove(invocation.getProxy());
            result = true;
        }
        return result;
    }


}
