package net.coolblossom.lycee.utils.file.entity.bind;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetterBinder implements FieldBinder {
    Method setterMethod;
    DataFileRule<?> rule;

    public SetterBinder(Method target, DataFileRule<?> rule) {
        this.setterMethod = target;
        this.rule = rule;
    }


    @Override
    public void bind(Object target, String value) throws InvocationTargetException, IllegalAccessException {
        setterMethod.invoke(target, rule.read(value));
    }
}
