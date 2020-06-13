package io.riguron.mocks;

import io.riguron.mocks.matcher.MatcherCapture;
import io.riguron.mocks.proxy.InvocationInterceptor;
import io.riguron.mocks.stub.CurrentStubbing;
import io.riguron.mocks.stub.LateStubbing;
import io.riguron.mocks.stub.Stubbing;
import io.riguron.mocks.verify.Verification;
import io.riguron.mocks.verify.VerificationMode;

import java.util.function.Supplier;

public final class Mocks {

    private static final MockProvider provider;
    private static final Verification verification;

    private Mocks() {
    }

    static {
        InvocationInterceptor invocationInterceptor = new InvocationInterceptor();
        CurrentStubbing currentStubbing = new CurrentStubbing();
        MatcherCapture matcherCapture = MatcherCapture.INSTANCE;
        verification = new Verification(invocationInterceptor, matcherCapture);
        provider = new MockProvider(invocationInterceptor, currentStubbing, verification, matcherCapture);
    }


    public static <T> Stubbing<T> when(T methodCall) {
        return provider.when(methodCall);
    }

    public static <T> LateStubbing<T> doAnswer(Answer<T> answer) {
        return provider.doAnswer(answer);
    }

    public static <T> LateStubbing<T> doNothing() {
        return doAnswer(x -> null);
    }

    public static <T> LateStubbing<T> doReturn(T t) {
        return provider.doReturn(t);
    }

    public static <T> LateStubbing<T> doThrow(Supplier<Throwable> exceptionProvider) {
        return provider.doThrow(exceptionProvider);
    }

    public static <T> T mock(Class<? extends T> type) {
        return provider.mock(type);
    }

    public static <T> T verify(T object, VerificationMode mode) {
        return verification.verify(object, mode);
    }

    public static <T> void verifyNoInteractions(T object) {
        verification.verifyNoInteractions(object);
    }

    public static void reset(Object mock) {
        verification.reset(mock);
    }

    public static <T> T verify(T object) {
        return verification.verify(object);
    }


}
