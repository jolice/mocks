package io.riguron.mocks.special;

public class PrivateConstructorClass {

    private PrivateConstructorClass(String value) {
        throw new IllegalStateException("Don't try to instantiate me with " + value);
    }
}
