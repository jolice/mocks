package io.riguron.mocks.tests;

import io.riguron.mocks.Mock;
import io.riguron.mocks.junit.MocksExtension;
import io.riguron.mocks.classes.SomeInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MocksExtension.class)
public class ExtensionTest {

    @Mock
    private SomeInterface someInterface;

    @Test
    void testMockInjected() {
        assertNotNull(someInterface);
        when(someInterface.getStringResult(eq(1), eq("Val"))).thenReturn("5");
        assertEquals("5", someInterface.getStringResult(1, "Val"));
    }
}
