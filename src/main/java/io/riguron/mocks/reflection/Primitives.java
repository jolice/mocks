package io.riguron.mocks.reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Primitives {

    private Primitives() {
    }

    private static final Map<Class<?>, Class<?>> wrappers = new HashMap<>();

    static {

        wrappers.put(Integer.class, int.class);
        wrappers.put(Long.class, long.class);
        wrappers.put(Double.class, double.class);
        wrappers.put(Float.class, float.class);
        wrappers.put(Character.class, char.class);
        wrappers.put(Short.class, short.class);
        wrappers.put(Byte.class, byte.class);
        wrappers.put(Boolean.class, boolean.class);
    }


    public static boolean equals(Class<?> objectType, Class<?> possiblePrimitive) {
        return Objects.equals(objectType, possiblePrimitive) || Objects.equals(wrappers.get(objectType), possiblePrimitive);
    }

}
