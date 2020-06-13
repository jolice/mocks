package io.riguron.mocks.exception;

public class InactiveStubbingException  extends RuntimeException{

    public InactiveStubbingException() {
        super(("Mocking is not active. Note that mocking of the final methods is not supported"));
    }
}
