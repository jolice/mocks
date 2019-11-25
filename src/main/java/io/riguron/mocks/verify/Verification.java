package io.riguron.mocks.verify;

import io.riguron.mocks.invocation.ArgumentMatcherEvaluation;
import io.riguron.mocks.invocation.Invocation;
import io.riguron.mocks.matcher.ArgumentMatcher;
import io.riguron.mocks.matcher.MatcherCapture;
import io.riguron.mocks.proxy.InvocationInterceptor;

import java.lang.reflect.Method;
import java.util.*;

import static io.riguron.mocks.verify.VerificationModes.times;

public final class Verification {

    private final InvocationInterceptor invocationInterceptor;
    private final MatcherCapture matcherCapture;
    private final Map<Object, Map<Method, Map<Invocation, Integer>>> invocations = new HashMap<>();

    public Verification(InvocationInterceptor invocationInterceptor, MatcherCapture matcherCapture) {
        this.invocationInterceptor = invocationInterceptor;
        this.matcherCapture = matcherCapture;
    }

    public <T> T verify(T object, VerificationMode mode) {
        invocationInterceptor.interceptNextCall(object, invocation -> {

            Method method = invocation.getMethod();

            Map<Method, Map<Invocation, Integer>> data = invocations.getOrDefault(object, Collections.emptyMap());
            Map<Invocation, Integer> invocationsForMethod = data.getOrDefault(method, Collections.emptyMap());

            List<ArgumentMatcher<?>> argumentMatchers = matcherCapture.popAll();
            int invocations = invocationsForMethod
                    .entrySet()
                    .stream()
                    .filter(x -> new ArgumentMatcherEvaluation(argumentMatchers, x.getKey()).evaluate())
                    .mapToInt(Map.Entry::getValue)
                    .sum();

            if (!mode.verify(invocations)) {
                throw new VerificationException(mode.error(invocations));
            }
        });
        return object;
    }

    public <T> void verifyNoInteractions(T object) {
        if (!invocations.getOrDefault(object, Collections.emptyMap()).isEmpty()) {
            throw new VerificationException("Expected no interactions");
        }
    }

    public void reset(Object mock) {
        invocations.remove(mock);
    }

    public <T> T verify(T object) {
        return verify(object, times(1));
    }

    public void handle(Invocation invocation) {
        this.invocations
                .computeIfAbsent(invocation.getProxy(), x -> new HashMap<>())
                .computeIfAbsent(invocation.getMethod(), x -> new HashMap<>())
                .merge(invocation, 1, Integer::sum);
    }


    public void reset(Object object, Method method, Invocation invocation) {
        Map<Method, Map<Invocation, Integer>> methods = invocations.getOrDefault(object, Collections.emptyMap());
        Map<Invocation, Integer> invocationsForMethod = methods.getOrDefault(method, Collections.emptyMap());
        if (invocationsForMethod.merge(invocation, 1, (x, y) -> x - y) == 0) {
            invocationsForMethod.remove(invocation);
            if (invocationsForMethod.isEmpty()) {
                methods.remove(method);
            }
        }
    }

}
