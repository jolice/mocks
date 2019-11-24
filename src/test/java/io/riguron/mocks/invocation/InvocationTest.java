package io.riguron.mocks.invocation;

import io.riguron.mocks.proxy.MockHandler;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static org.junit.jupiter.api.Assertions.*;

class InvocationTest {

    @Test
    void argumentsOutOfBounds() {

        Invocation invocation = new Invocation(
                new Object(),
                getClass().getDeclaredMethods()[0],
                mock(MockHandler.class),
                new Object[]{3, "4", 5L}
        );
        assertThrows(IndexOutOfBoundsException.class, () ->
                when(invocation.getArgument(10)));
    }
}