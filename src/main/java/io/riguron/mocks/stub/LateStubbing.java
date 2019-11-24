package io.riguron.mocks.stub;

import io.riguron.mocks.Answer;
import io.riguron.mocks.proxy.InvocationInterceptor;
import lombok.Value;

import java.util.Arrays;

@Value
public class LateStubbing<R> {

    private InvocationInterceptor invocationInterceptor;
    private Answer<R> answer;

    public <T> T when(T item) {
        invocationInterceptor.interceptNextCall(item, invocation -> {
            invocation.getMockHandler().createStubbing(invocation.getMethod(), answer);
        });
        return item;
    }
}
