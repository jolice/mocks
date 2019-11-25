package io.riguron.mocks.tests;

import io.riguron.mocks.Answer;
import io.riguron.mocks.classes.SomeInterface;
import io.riguron.mocks.invocation.Invocation;
import io.riguron.mocks.matcher.ArgumentMatcher;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static io.riguron.mocks.Mocks.*;
import static io.riguron.mocks.Mocks.doAnswer;
import static io.riguron.mocks.matcher.ArgumentMatchers.*;
import static io.riguron.mocks.verify.VerificationModes.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MockingOverridingTest {

    @Test
    void overrideWithSameArgsTwice() {

        SomeInterface someInterface = mock(SomeInterface.class);

        when(someInterface.getStringResult(eq(1), eq("1"))).thenReturn("A");

        assertEquals("A", someInterface.getStringResult(1, "1"));

        when(someInterface.getStringResult(eq(1), eq("1"))).thenReturn("B");

        assertEquals("B", someInterface.getStringResult(1, "1"));

        //

    }


    @Test
    void mockLocal() {
         UserRepository repository = mock(UserRepository.class);
         when(repository.getAge(any()));
         assertEquals(5, repository.getAge("Name"));
    }
    // Tutorial


    interface UserRepository {

        int getAge(String name);

        String findNameById(int id);

        int count();

        void updateName(int id, String name);
    }

    interface User {
    }


    @Test
    void overridePrefixStyle() {
        SomeInterface someInterface = mock(SomeInterface.class);


        doAnswer(new Answer<String>() {
            @Override
            public String answer(Invocation mockInvocation) throws Throwable {
                return "X";
            }

            @Override
            public String toString() {
                return "1";
            }
        }).when(someInterface).getStringResult(eq(1), eq("2"));

        assertEquals("X", someInterface.getStringResult(1, "2"));

        doAnswer(new Answer<String>() {
            @Override
            public String answer(Invocation mockInvocation) throws Throwable {
                return "Y";
            }

            @Override
            public String toString() {
                return "2";
            }
        }).when(someInterface).getStringResult(eq(1), eq("2"));
        assertEquals("Y", someInterface.getStringResult(1, "2"));

    }
}
