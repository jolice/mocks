package io.riguron.mocks.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.verify;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;


public class OrderedVerificationTest {

    private Command command;

    @BeforeEach
    void prepare() {
        command = mock(Command.class);

        when(command.one(eq(5))).thenReturn(1);
        when(command.two(eq(7))).thenReturn(2);
        command.one(5);
        command.two(7);

    }

    @Test
    void testOrderOneTwo() {

        verify(command).one(eq(5));
        verify(command).two(eq(7));

    }

    @Test
    void testOrderTwoOne() {
        verify(command).two(eq(7));
        verify(command).one(eq(5));


    }


    interface Command {

        int one(int x);

        int two(int y);

    }

    
}
