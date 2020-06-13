package io.riguron.mocks.classes;

import java.util.List;

public class SomeInterfaceImplementor implements SomeInterface {

    private static final String stringResult = "Return me";
    private static final int intResult = 100;

    public SomeInterfaceImplementor(int i) {
    }


    @Override
    public String getStringResult(int param, String value) {
        return stringResult;
    }

    @Override
    public int getIntResult(float param, List<Integer> values) {
        return intResult;
    }

    @Override
    public void somethingVoid(int x) {
        throw new UnsupportedOperationException("Get Me");
    }
}
