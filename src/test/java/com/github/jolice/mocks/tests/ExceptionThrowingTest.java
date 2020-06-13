package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.any;
import static io.riguron.mocks.matcher.ArgumentMatchers.anyFloat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionThrowingTest {

    @Test
    void thenThrow() {
        SomeInterface someInterface = mock(SomeInterface.class);
        when(someInterface.getIntResult(anyFloat(), any())).thenThrow(UnsupportedOperationException::new);

        assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                someInterface.getIntResult(2F, Collections.emptyList());
            }
        });
    }
}
