package io.riguron.mocks.junit;

import io.riguron.mocks.Mock;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

import static io.riguron.mocks.Mocks.mock;

public class MocksExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object o, ExtensionContext extensionContext) throws Exception {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Mock.class)) {
                field.setAccessible(true);
                field.set(o, mock(field.getType()));
            }
        }
    }
}
