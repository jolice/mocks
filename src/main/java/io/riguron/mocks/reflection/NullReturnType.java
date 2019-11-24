package io.riguron.mocks.reflection;

import java.lang.reflect.Method;

public class NullReturnType {

    private Method method;

    public NullReturnType(Method method) {
        this.method = method;
    }

    public Object determine() {
        if (method.getReturnType() == int.class) {
            return 0;
        } else if (method.getReturnType() == double.class) {
            return 0D;
        } else if (method.getReturnType() == float.class) {
            return 0F;
        } else if (method.getReturnType() == boolean.class) {
            return false;
        } else if (method.getReturnType() == char.class) {
            return Character.MIN_VALUE;
        } else if (method.getReturnType() == long.class) {
            return 0L;
        } else if (method.getReturnType() == byte.class) {
            return (byte) 0;
        } else if (method.getReturnType() == short.class) {
            return (short) 0;
        }
        return null;
    }
}
