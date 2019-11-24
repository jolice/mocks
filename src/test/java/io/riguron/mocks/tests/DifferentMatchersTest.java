package io.riguron.mocks.tests;

import io.riguron.mocks.matcher.ArgumentMatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.doReturn;
import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.matcher.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DifferentMatchersTest {

    private AllArguments allArguments;

    @BeforeEach
    void prepareMock() {
        allArguments = mock(AllArguments.class);
    }

    @Test
    void testAny() {
        doReturn(5D).when(allArguments).run(anyDouble());
        doReturn(5).when(allArguments).run(anyInt());
        doReturn(5F).when(allArguments).run(anyFloat());
        doReturn(2L).when(allArguments).run(anyLong());
        doReturn(true).when(allArguments).run(anyBoolean());
        doReturn('c').when(allArguments).run(anyChar());
        doReturn((short) 5).when(allArguments).run(anyShort());
        doReturn((byte) 5).when(allArguments).run(anyByte());
        doReturn("X").when(allArguments).run(any());
    }

    @Test
    void testEq() {
        doReturn(5D).when(allArguments).run(eq(1D));
        doReturn(5).when(allArguments).run(eq(1));
        doReturn(5F).when(allArguments).run(eq(1F));
        doReturn(2L).when(allArguments).run(eq(2L));
        doReturn(true).when(allArguments).run(eq(false));
        doReturn('c').when(allArguments).run(eq('b'));
        doReturn((short) 5).when(allArguments).run(eq((short) 5));
        doReturn((byte) 5).when(allArguments).run(eq((byte) 5));
        doReturn("X").when(allArguments).run(eq("Y"));
        doReturn(100).when(allArguments).run(argThat(new FortyTwoMatcher()));
        assertEquals(100, allArguments.run((Integer) 42));
    }

    @AfterEach
    void runAssertion() {
        doAssert();
    }

    private void doAssert() {
        assertEquals(5D, allArguments.run(1D));
        assertEquals(5, allArguments.run(1));
        assertEquals(5F, allArguments.run(1F));
        assertEquals(2L, allArguments.run(2L));
        assertTrue(allArguments.run(false));
        assertEquals('c', allArguments.run('b'));
        assertEquals((short) 5, allArguments.run((short) 5));
        assertEquals((byte) 5, allArguments.run((byte) 5));
        assertEquals("X", allArguments.run("Y"));

    }



    interface AllArguments {

        double run(double d);

        int run(int i);

        float run(float f);

        long run(long l);

        boolean run(boolean b);

        char run(char c);

        short run(short s);

        byte run(byte b);

        Object run(Object o);

    }

    private static class FortyTwoMatcher implements ArgumentMatcher<Integer> {

        @Override
        public boolean matches(Integer integer) {
            return integer == 42;
        }
    }
}
