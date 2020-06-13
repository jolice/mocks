package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import io.riguron.mocks.exception.matcher.ExtraMatchersException;
import io.riguron.mocks.exception.matcher.InsufficientMatchersExceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidMatchersTest {

    @Test
    void notEnoughMatchers() {

        SomeInterface someInterface = mock(SomeInterface.class);

        when(someInterface.getIntResult(2F, Arrays.asList(1, 2, 3)))
                .thenReturn(5);

        assertThrows(InsufficientMatchersExceptions.class, () -> someInterface.getIntResult(2F, Arrays.asList(2, 3, 4)));

    }

    @Test
    void tooManyMatchers() {

        SomeInterface someInterface = mock(SomeInterface.class);
        // Extra matcher
        eq(5);
        when(someInterface.getIntResult(eq(2F), eq(Arrays.asList(1, 2, 3))))
                .thenReturn(5);


        assertThrows(ExtraMatchersException.class, () -> someInterface.getIntResult(2F, Arrays.asList(2, 3, 4)));
    }

}
