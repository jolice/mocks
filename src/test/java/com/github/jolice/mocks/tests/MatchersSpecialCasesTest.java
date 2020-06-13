package io.riguron.mocks.tests;

import io.riguron.mocks.exception.UnsupportedMatcherTypeException;
import io.riguron.mocks.matcher.ArgumentMatcher;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.argThat;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchersSpecialCasesTest {


    @Test
    void lambdaFails() {

        SomeInterface someInterface = mock(SomeInterface.class);
        when(someInterface.run(argThat((ArgumentMatcher<String>) s -> true))).thenReturn(5);
        assertThrows(UnsupportedMatcherTypeException.class, () -> someInterface.run(500));
    }

    @Test
    void nullArgumentPasses() {
        SomeInterface someInterface = mock(SomeInterface.class);
        when(someInterface.run(argThat((ArgumentMatcher<String>) s -> true))).thenReturn(5);
        assertEquals(5, someInterface.run(null));
    }

    @Test
    void whenArgumentDoesNotPass() {
        SomeInterface someInterface = mock(SomeInterface.class);
        when(someInterface.run(argThat(new IntegerMatcher()))).thenReturn(5);
        assertEquals(0, someInterface.run("X"));
    }

    private static class IntegerMatcher implements ArgumentMatcher<Integer> {

        @Override
        public boolean matches(Integer integer) {
            return true;
        }
    }

    private interface SomeInterface {

        int run(Object object);
    }
}
