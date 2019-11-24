package io.riguron.mocks.stub;

import io.riguron.mocks.Answer;
import io.riguron.mocks.proxy.MockHandler;

import java.util.function.Supplier;

public class Stubbing<T> {

    private CurrentStubbing currentStubbing;
    private MockHandler mockHandler;

    public Stubbing(MockHandler mockHandler, CurrentStubbing currentStubbing) {
        this.mockHandler = mockHandler;
        this.currentStubbing = currentStubbing;
        this.mockHandler.setConfiguring(true);
    }

    public void thenReturn(T t) {
        mockHandler.createStubbing(currentStubbing.getMethod(), x -> t);
    }

    public void thenAnswer(Answer<?> answer) {
        mockHandler.createStubbing(currentStubbing.getMethod(), answer);
    }

    public void thenThrow(Supplier<Throwable> throwable) {
        mockHandler.createStubbing(currentStubbing.getMethod(), (mockInvocation -> {
            throw throwable.get();
        }));
    }


}


