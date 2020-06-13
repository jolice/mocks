package io.riguron.mocks.tests;

import io.riguron.mocks.exception.UnfinishedStubbingException;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnfinishedStubbingTest {

    @Test
    void normalStyle() {
        SomeInterface someInterface = mock(SomeInterface.class);
        when(someInterface.result());
        assertThrows(UnfinishedStubbingException.class, someInterface::result);
    }

    private interface SomeInterface {

        int result();
    }
}
