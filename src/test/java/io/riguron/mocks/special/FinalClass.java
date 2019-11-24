package io.riguron.mocks.special;

import lombok.Getter;

public final class FinalClass {

    @Getter
    private final int value;

    public FinalClass(int value) {
        this.value = value;
    }

}
