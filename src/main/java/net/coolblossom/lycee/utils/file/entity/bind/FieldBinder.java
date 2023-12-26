package net.coolblossom.lycee.utils.file.entity.bind;

import java.lang.reflect.InvocationTargetException;

public interface FieldBinder {

    void bind(Object target, String value) throws InvocationTargetException, IllegalAccessException;
}
