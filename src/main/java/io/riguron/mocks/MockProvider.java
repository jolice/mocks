package io.riguron.mocks;


import io.riguron.mocks.exception.InactiveStubbingException;
import io.riguron.mocks.matcher.MatcherCapture;
import io.riguron.mocks.proxy.InvocationInterceptor;
import io.riguron.mocks.proxy.MockHandler;
import io.riguron.mocks.proxy.ProxyGenerator;
import io.riguron.mocks.stub.CurrentStubbing;
import io.riguron.mocks.stub.LateStubbing;
import io.riguron.mocks.stub.Stubbing;
import io.riguron.mocks.stub.StubbingData;
import io.riguron.mocks.verify.Verification;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class MockProvider {

    private final InvocationInterceptor interceptor;
    private final CurrentStubbing currentStubbing;
    private final Verification verification;
    private final MatcherCapture matcherCapture;

    public MockProvider(InvocationInterceptor interceptor, CurrentStubbing currentStubbing, Verification verification, MatcherCapture matcherCapture) {
        this.interceptor = interceptor;
        this.currentStubbing = currentStubbing;
        this.verification = verification;
        this.matcherCapture = matcherCapture;
    }

    public <T> Stubbing<T> when(T methodCall) {
        // СОздать регистр текущих моков, чтобы проверять, делается ли вызов в контексте мока или нет.
        if (!currentStubbing.isActive()) {
            throw new InactiveStubbingException();
        }
        StubbingData currentData = currentStubbing.getStubbingData();
        verification.reset(currentData.getProxy(), currentData.getMethod(), currentData.getInvocation());
        return new Stubbing<>(currentData.getCurrentMock(), currentStubbing);
    }

    public <T> LateStubbing<T> doAnswer(Answer<T> answer) {
        return new LateStubbing<>(interceptor, answer);
    }

    public <T> LateStubbing<T> doReturn(T t) {
        return doAnswer(x -> t);
    }

    public <T> LateStubbing<T> doThrow(Supplier<Throwable> exceptionProvider) {
        return doAnswer(x -> {
            throw exceptionProvider.get();
        });
    }

    public <T> T mock(Class<?> type) {
        return (T) new ProxyGenerator(type, new MockHandler(matcherCapture, currentStubbing, interceptor, verification)).generate();
    }
}
