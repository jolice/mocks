package io.riguron.mocks.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CommonMethodsProxy implements InvocationHandler {

    private final InvocationHandler delegate;
    private final Class<?> type;

    public CommonMethodsProxy(InvocationHandler delegate, Class<?> type) {
        this.delegate = delegate;
        this.type = type;
    }

    private boolean isHashCode(Method method) {
        return method.getName().equals("hashCode")
                && method.getReturnType().equals(int.class)
                && method.getParameterCount() == 0;
    }

    private boolean isToString(Method method) {
        return method.getName().equals("toString")
                && method.getReturnType().equals(String.class)
                && method.getParameterCount() == 0;
    }

    private boolean isEquals(Method method) {
        return method.getName().equals("equals")
                && method.getReturnType().equals(boolean.class)
                && method.getParameters()[0].getType().equals(Object.class)
                && method.getParameterCount() == 1;


    }

    @Override
    public Object invoke(Object self, Method method, Object[] args) throws Throwable {
        if (isHashCode(method)) {
            return System.identityHashCode(self);
        } else if (isToString(method)) {
            return "Mock for " + type + ", hashCode = " + System.identityHashCode(self);
        } else if (isEquals(method)) {
            return args[0] == self;
        }
        return delegate.invoke(self, method, args);
    }
}
