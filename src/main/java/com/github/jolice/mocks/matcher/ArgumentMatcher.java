package io.riguron.mocks.matcher;

public interface ArgumentMatcher<T> {

    boolean matches(T t);

}
