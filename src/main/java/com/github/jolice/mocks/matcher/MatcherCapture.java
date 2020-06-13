package io.riguron.mocks.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum MatcherCapture {

    INSTANCE;

    private final List<ArgumentMatcher<?>> arguments = new ArrayList<>();

    public void addMatcher(ArgumentMatcher<?> matcher) {
        this.arguments.add(matcher);
    }

    public List<ArgumentMatcher<?>> popAll() {
        List<ArgumentMatcher<?>> matchers = Collections.unmodifiableList(new ArrayList<>(this.arguments));
        this.arguments.clear();
        return matchers;
    }

}
