package io.riguron.mocks.exception.matcher;

import java.util.function.Supplier;

public abstract class InvalidUseOfMatchersException extends RuntimeException {

    public InvalidUseOfMatchersException(int argumentCount, Supplier<String> messageProducer) {
        super(String.format("Invalid use of matchers. %d arguments require exactly %d matchers, but %s",
                argumentCount,
                argumentCount,
                messageProducer.get()
        ));
    }

}
