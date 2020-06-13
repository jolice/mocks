package io.riguron.mocks.exception.matcher;

public class InsufficientMatchersExceptions extends InvalidUseOfMatchersException {

    public InsufficientMatchersExceptions(int args, int matchers) {
        super(args, () -> String.format("only %d matchers are specified, lacking %d matchers", matchers, args - matchers));
    }
}
