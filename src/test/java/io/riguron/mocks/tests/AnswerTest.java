package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.any;
import static io.riguron.mocks.matcher.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerTest {

    @Test
    void answer() {
        SomeInterface someInterface = mock(SomeInterface.class);

        when(someInterface.getStringResult(anyInt(), any())).thenAnswer(mockInvocation -> {
            int first = mockInvocation.getArgument(0);
            String second = mockInvocation.getArgument(1);
            return String.format("%s %d", second, first);
        });

        assertEquals("Value 2", someInterface.getStringResult(2, "Value"));
    }
}
