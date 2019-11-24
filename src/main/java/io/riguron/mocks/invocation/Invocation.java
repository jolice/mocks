package io.riguron.mocks.invocation;

import io.riguron.mocks.proxy.MockHandler;
import lombok.Value;

import java.lang.reflect.Method;

@Value
@SuppressWarnings("unchecked")
public class Invocation {

    private Object proxy;
    private Method method;
    private MockHandler mockHandler;
    private Object[] arguments;

    public <T> T getArgument(int index) {
        if (index < 0 || index >= arguments.length)
            throw new IndexOutOfBoundsException(String.format("Invalid index: %d, argument length: %d", index, arguments.length));
        Object target = arguments[index];
        return (T) target;
    }

}
