package io.riguron.mocks.special;

import io.riguron.mocks.MockingException;
import io.riguron.mocks.exception.InactiveStubbingException;
import io.riguron.mocks.exception.MockCreationException;
import io.riguron.mocks.matcher.ArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import static io.riguron.mocks.Mocks.*;
import static io.riguron.mocks.matcher.ArgumentMatchers.argThat;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialCaseMockingTest {



    @Test
    void mockFinalClass() {
        assertThrows(MockCreationException.class, () -> {
            FinalClass mock = mock(FinalClass.class);
            when(mock.getValue()).thenReturn(5);
        });
    }

    @Test
    void mockFinal() {
        FinalMethod mock = mock(FinalMethod.class);
        when(mock.resultNonFinal()).thenReturn(5);
        assertThrows(InactiveStubbingException.class, () -> when(mock.result()).thenReturn(6));
    }

    @Test
    void mockAbstractClass() {
        final AbstractClass abstractClass = mock(AbstractClass.class);
        when(abstractClass.getValue()).thenReturn("X");
    }

    @Test
    void mockPrivateConstructor() {
        mock(PrivateConstructorClass.class);
    }
}
