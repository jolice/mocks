package io.riguron.mocks.tests;

import io.riguron.mocks.classes.SomeInterface;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.mock;
import static org.junit.jupiter.api.Assertions.*;

public class CommonMethodsTest {

    @Test
    void testEquals() {
        ClassWithFakeBasicMethods someInterface = mock(ClassWithFakeBasicMethods.class);
        assertFalse(someInterface.equals("X", 0));
        assertEquals(someInterface, someInterface);

    }

    @Test
    void testHashCode() {
        ClassWithFakeBasicMethods someInterface = mock(ClassWithFakeBasicMethods.class);
        assertEquals(0, someInterface.hashCode(5));
        assertFalse(someInterface.hashCode() == 0);
    }


    @Test
    void testToString() {
        ClassWithFakeBasicMethods someInterface = mock(ClassWithFakeBasicMethods.class);
        assertNull(someInterface.toString(5));
        assertNotNull(someInterface.toString());
        assertFalse(someInterface.toString().length() == 0);
    }

    static class ClassWithFakeBasicMethods {

        public boolean equals(Object x, int i) {
            return true;
        }

        public int hashCode(int val) {
            return 100;
        }

        public String toString(int val) {
            return "Fake!";
        }


    }
}
