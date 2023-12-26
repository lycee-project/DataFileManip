package net.coolblossom.lycee.utils.file.entity.bind;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

import java.lang.reflect.Field;

public class DirectFieldBinder implements FieldBinder {

    private final Field field;
    private final DataFileRule<?> rule;

    public DirectFieldBinder(Field field, DataFileRule<?> rule) {
        this.field = field;
        this.rule = rule;
    }

    @Override
    public void bind(Object target, String value) throws IllegalAccessException {
        field.set(target, rule.read(value));
    }
}
