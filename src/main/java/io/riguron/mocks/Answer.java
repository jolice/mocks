package io.riguron.mocks;

import io.riguron.mocks.invocation.Invocation;

public interface Answer<T> {
    T answer(Invocation mockInvocation) throws Throwable;
}
