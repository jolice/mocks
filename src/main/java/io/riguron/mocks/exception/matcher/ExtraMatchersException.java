package io.riguron.mocks.exception.matcher;

public class ExtraMatchersException extends InvalidUseOfMatchersException {

    public ExtraMatchersException(int args, int matchers) {
        super(args, () -> String.format("%d extra matchers were specified", matchers - args));
    }
}
