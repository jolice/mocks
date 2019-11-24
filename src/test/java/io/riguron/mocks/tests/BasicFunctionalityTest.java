package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import io.riguron.mocks.classes.SomeInterfaceImplementor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BasicFunctionalityTest {

    @Test
    void plainTestMockInterface() {
        testMock(SomeInterface.class);
    }

    @Test
    void plainMockClass() {
        testMock(SomeInterfaceImplementor.class);
    }
    private void testMock(Class<? extends SomeInterface> testMock) {
        SomeInterface someInterface = mock(testMock);

        assertNull(someInterface.getStringResult(0, "Value"));

        when(someInterface.getStringResult(eq(5), eq("Value"))).thenReturn("Result");
        when(someInterface.getIntResult(eq(2F), eq(Arrays.asList(1, 2, 3)))).thenReturn(10);

        assertEquals("Result", someInterface.getStringResult(5, "Value"));
        assertNull(someInterface.getStringResult(5, "Wrong"));

        assertEquals(10, someInterface.getIntResult(2F, Arrays.asList(1, 2, 3)));
        assertEquals(0, someInterface.getIntResult(2.1F, Arrays.asList(1, 2, 3)));
    }

}
