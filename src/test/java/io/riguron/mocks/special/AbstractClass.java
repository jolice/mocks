package io.riguron.mocks.special;

import lombok.Getter;

public abstract class AbstractClass {

    @Getter
    private final String value;

    public AbstractClass(String value) {
        this.value = value;
    }
}
