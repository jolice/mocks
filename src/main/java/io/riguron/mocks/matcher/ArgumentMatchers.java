package io.riguron.mocks.matcher;

import io.riguron.mocks.reflection.Primitives;

@SuppressWarnings("unchecked")
public class ArgumentMatchers {


    private ArgumentMatchers() {
    }

    public static <T> T eq(T value) {
        addEqualityMatcher(value);
        return nothing();
    }

    public static <T> T any() {
        addMatcher(x -> true);
        return nothing();
    }

    public static int anyInt() {
        addAnyPrimitiveMatcher(int.class);
        return 0;
    }

    public static double anyDouble() {
        addAnyPrimitiveMatcher(double.class);
        return 0;
    }

    public static long anyLong() {
        addAnyPrimitiveMatcher(long.class);
        return 0;
    }

    public static float anyFloat() {
        addAnyPrimitiveMatcher(float.class);
        return 0;
    }

    public static char anyChar() {
        addAnyPrimitiveMatcher(char.class);
        return Character.MIN_VALUE;
    }

    public static short anyShort() {
        addAnyPrimitiveMatcher(short.class);
        return 0;
    }

    public static byte anyByte() {
        addAnyPrimitiveMatcher(byte.class);
        return 0;
    }

    public static boolean anyBoolean() {
        addAnyPrimitiveMatcher(boolean.class);
        return false;
    }

    public static int eq(int value) {
        addEqualityMatcher(value);
        return 0;
    }

    public static long eq(long value) {
        addEqualityMatcher(value);
        return 0;
    }

    public static double eq(double value) {
        addEqualityMatcher(value);
        return value;
    }

    public static float eq(float value) {
        addEqualityMatcher(value);
        return 0;
    }

    public static boolean eq(boolean value) {
        addEqualityMatcher(value);
        return false;
    }

    public static short eq(short value) {
        addEqualityMatcher(value);
        return 0;
    }

    public static byte eq(byte value) {
        addEqualityMatcher(value);
        return 0;
    }

    public static <T> T argThat(ArgumentMatcher<T> matcher) {
        addMatcher(matcher);
        return nothing();
    }

    public static char eq(char value) {
        addEqualityMatcher(value);
        return Character.MIN_VALUE;
    }

    private static void addEqualityMatcher(Object value) {
        addMatcher(new EqualityMatcher<>(value));
    }

    private static void addAnyPrimitiveMatcher(Class<?> type) {
        addMatcher(x -> Primitives.equals(x.getClass(), type));
    }

    private static void addMatcher(ArgumentMatcher<?> matcher) {
        MatcherCapture.INSTANCE.addMatcher(matcher);
    }

    private static <T> T nothing() {
        return (T) NullReference.VALUE;
    }


}
