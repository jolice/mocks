package io.riguron.mocks.special;

import io.riguron.mocks.exception.InactiveStubbingException;
import io.riguron.mocks.exception.MockCreationException;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("X", abstractClass.getValue());
    }

    @Test
    void mockPrivateConstructor() {
        PrivateConstructorClass privateConstructorClass = mock(PrivateConstructorClass.class);
        assertNotNull(privateConstructorClass);
    }
}
