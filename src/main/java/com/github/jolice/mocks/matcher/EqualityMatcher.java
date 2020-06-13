package io.riguron.mocks.matcher;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@EqualsAndHashCode
@ToString
public class EqualityMatcher<T> implements ArgumentMatcher<T>{

    private T value;

    public EqualityMatcher(T value) {
        this.value = value;
    }

    @Override
    public boolean matches(T t) {
        return Objects.equals(t, value);
    }
}
