package io.riguron.mocks.classes;

import java.util.List;

public interface SomeInterface {

    String getStringResult(int param, String value);

    int getIntResult(float param, List<Integer> values);

    void somethingVoid(int x);

}
