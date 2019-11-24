package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import io.riguron.mocks.verify.VerificationException;
import io.riguron.mocks.verify.VerificationModes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static io.riguron.mocks.Mocks.*;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static io.riguron.mocks.verify.VerificationModes.times;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VerificationTest {

    @Test
    void verificationWithoutSettings() {
        SingleMethod singleMethod = mock(SingleMethod.class);

        singleMethod.run();

        verify(singleMethod, times(1)).run();
    }

    @Test
    void verifyConfigurationMethodDoesNotVerify() {

        SingleMethod singleMethod = mock(SingleMethod.class);
        when(singleMethod.run()).thenReturn(5);

        verifyNoInteractions(singleMethod);
    }

    @Test
    void singleMethodVerifies() {
        SingleMethod singleMethod = mock(SingleMethod.class);
        when(singleMethod.run()).thenReturn(5);
        singleMethod.run();
        verify(singleMethod).run();
    }

    interface SingleMethod {

        int run();
    }

    @Test
    void testVerify() {

        SomeInterface someInterface = mock(SomeInterface.class);

        doNothing().when(someInterface).somethingVoid(eq(5));

        someInterface.somethingVoid(5);
        someInterface.somethingVoid(5);
        someInterface.somethingVoid(6);

        verify(someInterface, times(2)).somethingVoid(eq(5));
        verify(someInterface, times(2)).somethingVoid(eq(5));

    }

    @Test
    void testNone() {

        SomeInterface someInterface = mock(SomeInterface.class);

        doNothing().when(someInterface).somethingVoid(eq(5));

        verifyNoInteractions(someInterface);
    }

    @Test
    void testReset() {

        SomeInterface someInterface = mock(SomeInterface.class);

        doNothing().when(someInterface).somethingVoid(eq(3));

        for (int i = 0; i < 5; i++) {
            someInterface.somethingVoid(3);
        }

        verify(someInterface, times(5)).somethingVoid(eq(3));
        reset(someInterface);
        verifyNoInteractions(someInterface);

    }

    @Test
    void testWrongVerification() {
        SomeInterface someInterface = mock(SomeInterface.class);

        doNothing().when(someInterface).somethingVoid(eq(5));
        someInterface.somethingVoid(5);

        assertThrows(VerificationException.class, () -> verify(someInterface, times(2)).somethingVoid(eq(5)));
    }

    @Test
    void noVerificationsExpectedByActuallyGotSome() {
        SomeInterface someInterface = mock(SomeInterface.class);

        doNothing().when(someInterface).somethingVoid(eq(5));
        someInterface.somethingVoid(5);

        assertThrows(VerificationException.class, () -> verifyNoInteractions(someInterface));
    }
}
