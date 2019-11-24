package io.riguron.mocks.verify;

public interface VerificationMode {

    boolean verify(int times);

    String error(int actual);

}
