package io.riguron.mocks.stub;

import io.riguron.mocks.invocation.Invocation;
import io.riguron.mocks.proxy.MockHandler;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
public class StubbingData {

    private final MockHandler currentMock;

    @Delegate
    private final Invocation invocation;

}
