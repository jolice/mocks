package io.riguron.mocks.proxy;

import io.riguron.mocks.invocation.Invocation;

public interface Interceptor {

    void interceptCall(Invocation invocation);

}
