package io.riguron.mocks.invocation;

import io.riguron.mocks.exception.UnsupportedMatcherTypeException;
import io.riguron.mocks.exception.matcher.ExtraMatchersException;
import io.riguron.mocks.exception.matcher.InsufficientMatchersExceptions;
import io.riguron.mocks.matcher.ArgumentMatcher;

import java.util.Arrays;
import java.util.List;

public class ArgumentMatcherEvaluation {

    private List<ArgumentMatcher<?>> matchers;
    private Invocation invocation;

    public ArgumentMatcherEvaluation(List<ArgumentMatcher<?>> matchers, Invocation invocation) {
        this.matchers = matchers;
        this.invocation = invocation;
    }

    public boolean evaluate() {
        this.validateMatchers(matchers, invocation.getArguments());

        for (int i = 0; i < matchers.size(); i++) {

            if (!argumentApplies(matchers.get(i), invocation.getArgument(i))) {
                return false;
            }

            try {
                if (!matchers.get(i).matches(invocation.getArgument(i))) {
                    return false;
                }
            } catch (ClassCastException e) {
                throw new UnsupportedMatcherTypeException();
            }
        }
        return true;
    }

    private boolean argumentApplies(ArgumentMatcher<?> argumentMatcher, Object argument) {
        return argument == null || getArgumentType(argumentMatcher).isInstance(argument);
    }

    private static Class<?> getArgumentType(ArgumentMatcher<?> argumentMatcher) {
        return
                Arrays.stream(argumentMatcher.getClass().getMethods())
                        .filter(method -> method.getParameterCount() == 1 && "matches".equals(method.getName()) && !method.isBridge())
                        .findAny()
                        .map(method -> method.getParameterTypes()[0])
                        .orElseThrow(() -> new IllegalStateException("Invalid matcher argument!"));
    }

    private void validateMatchers(List<ArgumentMatcher<?>> matchers, Object[] args) {
        if (matchers.size() != args.length) {
            if (matchers.size() > args.length) {
                throw new ExtraMatchersException(args.length, matchers.size());
            } else {
                throw new InsufficientMatchersExceptions(args.length, matchers.size());
            }
        }
    }
}
