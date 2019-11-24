package io.riguron.mocks.proxy;

import io.riguron.mocks.exception.MockCreationException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.ObjenesisHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;

;

public class ProxyGenerator {

    private Class<?> type;
    private InvocationHandler handler;

    public ProxyGenerator(Class<?> type, InvocationHandler handler) {
        this.type = type;
        this.handler = handler;
    }

    public Object generate() {
        this.validate();
        return ObjenesisHelper.newInstance(new ByteBuddy()
                .subclass(type)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(new CommonMethodsProxy(handler, type)))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded());

    }

    private void validate() {
        if (Modifier.isFinal(type.getModifiers())) {
            throw new MockCreationException("Can't mock a final class");
        }
    }
}
