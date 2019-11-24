package io.riguron.mocks.invocation;

import io.riguron.mocks.Answer;
import io.riguron.mocks.matcher.ArgumentMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
public class CallStubbing {


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Answer<?> answer;


    private List<ArgumentMatcher<?>> matchers;



}
