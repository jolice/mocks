package io.riguron.mocks.tests;

import io.riguron.mocks.classes.ChangeDetector;
import io.riguron.mocks.classes.SomeInterface;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.riguron.mocks.Mocks.*;
import static io.riguron.mocks.Mocks.doReturn;
import static io.riguron.mocks.matcher.ArgumentMatchers.*;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrefixStyleMockingTest {

    @Test
    void mockVoidMethod() {

        ChangeDetector changeDetector = new ChangeDetector();

        SomeInterface someInterface = mock(SomeInterface.class);

        doAnswer(mockInvocation -> {
            changeDetector.change();
            return null;
        }).when(someInterface).somethingVoid(eq(5));

        someInterface.somethingVoid(5);

        assertTrue(changeDetector.wasChanged());


    }

    @Test
    void voidMethodThrows() {

        SomeInterface someInterface = mock(SomeInterface.class);

        doThrow(UnsupportedOperationException::new).when(someInterface).somethingVoid(eq(3));

        assertThrows(UnsupportedOperationException.class, () -> someInterface.somethingVoid(3));

    }

    @Test
    void doReturnSyntax() {

        SomeInterface someInterface = mock(SomeInterface.class);

        doReturn(5).when(someInterface).getIntResult(anyFloat(), any());
        doReturn("X").when(someInterface).getStringResult(eq(2), eq("Val"));

        assertEquals(5, someInterface.getIntResult(2F, Arrays.asList(1, 2, 3)));
        assertEquals("X", someInterface.getStringResult(2, "Val"));


    }
}
