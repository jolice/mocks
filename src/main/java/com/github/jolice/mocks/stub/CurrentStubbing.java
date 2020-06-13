package io.riguron.mocks.stub;

import lombok.Getter;
import lombok.experimental.Delegate;

import java.lang.reflect.Method;

public class CurrentStubbing {

    @Getter
    private StubbingData stubbingData;

    public void stub(StubbingData data) {
        this.stubbingData = data;
    }

    public void discard() {
        this.stubbingData = null;
    }

    public boolean isActive() {
        return stubbingData != null;
    }

    public Method getMethod() {
        return stubbingData.getMethod();
    }

}
