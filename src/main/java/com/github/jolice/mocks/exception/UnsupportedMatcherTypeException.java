package io.riguron.mocks.exception;

public class UnsupportedMatcherTypeException extends RuntimeException {

    public UnsupportedMatcherTypeException() {
        super("Unsupported matcher passed. If you use lambda expression or any generic components as an argument matcher, replace it with the anonymous or inner/standard class.");
    }
}
