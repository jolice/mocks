package io.riguron.mocks.verify;

public class VerificationModes {

    private VerificationModes() {
    }

    public static VerificationMode times(int size) {
        return new VerificationMode() {
            @Override
            public boolean verify(int times) {
                return size == times;
            }

            @Override
            public String error(int actual) {
                return String.format("Expected %d invocations, but got %d", size, actual);
            }
        };
    }


}
