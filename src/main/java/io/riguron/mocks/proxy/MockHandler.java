package io.riguron.mocks.proxy;

import io.riguron.mocks.Answer;
import io.riguron.mocks.exception.UnfinishedStubbingException;
import io.riguron.mocks.invocation.ArgumentMatcherEvaluation;
import io.riguron.mocks.invocation.CallStubbing;
import io.riguron.mocks.invocation.Invocation;
import io.riguron.mocks.matcher.MatcherCapture;
import io.riguron.mocks.reflection.NullReturnType;
import io.riguron.mocks.stub.CurrentStubbing;
import io.riguron.mocks.stub.StubbingData;
import io.riguron.mocks.verify.Verification;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class MockHandler implements InvocationHandler {

    private final MatcherCapture matcherCapture;
    private final Map<Method, Set<CallStubbing>> configuration = new HashMap<>();
    private final CurrentStubbing stubbing;
    private final InvocationInterceptor invocationInterceptor;
    private final Verification verification;
    private boolean configuring;

    public MockHandler(MatcherCapture matcherCapture, CurrentStubbing stubbing, InvocationInterceptor invocationInterceptor, Verification verification) {
        this.matcherCapture = matcherCapture;
        this.stubbing = stubbing;
        this.invocationInterceptor = invocationInterceptor;
        this.verification = verification;
    }

    @Override
    public Object invoke(Object self, Method method, Object[] args) throws Throwable {


        this.checkNotConfiguring();
        Invocation invocation = new Invocation(self, method, this, args);
        this.stubbing.stub(new StubbingData(this, invocation));

        if (!(invocationInterceptor.handleCall(invocation))) {
            verification.handle(invocation);

            Set<CallStubbing> call = configuration.getOrDefault(method, Collections.emptySet());
            if (!call.isEmpty()) {
                return doAnswer(call, invocation);
            }
        }

        return getNull(method);

    }

    private void checkNotConfiguring() {
        if (configuring) {
            throw new UnfinishedStubbingException();
        }
    }

    public void setConfiguring(boolean configuring) {
        this.configuring = configuring;
    }

    public void createStubbing(Method method, Answer<?> answer) {
        this.addData(method, answer);
        this.configuring = false;
    }

    private void addData(Method method, Answer<?> answer) {
        CallStubbing callStubbing = new CallStubbing(answer, matcherCapture.getAll());
        Set<CallStubbing> data = this.configuration.computeIfAbsent(method, x -> new HashSet<>());
        data.remove(callStubbing);
        data.add(callStubbing);
        matcherCapture.reset();
        stubbing.discard();
    }

    private Object doAnswer(Set<CallStubbing> call, Invocation invocation) throws Throwable {
        Optional<CallStubbing> result = call
                .stream()
                .filter(x -> new ArgumentMatcherEvaluation(x.getMatchers(), invocation).evaluate())
                .findFirst();

        if (result.isPresent()) {

            return result.get().getAnswer().answer(invocation);
        } else {
            return getNull(invocation.getMethod());
        }
    }

    private Object getNull(Method method) {
        return new NullReturnType(method).determine();
    }


}
