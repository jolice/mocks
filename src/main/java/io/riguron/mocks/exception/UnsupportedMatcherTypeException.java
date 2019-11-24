package io.riguron.mocks.exception;

import static io.riguron.mocks.matcher.ArgumentMatchers.eq;

public class UnsupportedMatcherTypeException extends RuntimeException {

    public UnsupportedMatcherTypeException() {
        super("Unsupported matcher passed. If you use lambda expression or any generic components as an argument matcher, replace it with the anonymous or inner/standard class.");
    }
}
