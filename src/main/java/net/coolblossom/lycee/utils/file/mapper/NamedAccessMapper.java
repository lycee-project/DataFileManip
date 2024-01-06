package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.entity.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class NamedAccessMapper<T> extends DefaultEntityMapper<T> {

    private final List<Field> fieldList;

    public NamedAccessMapper(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);
        this.fieldList = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .toList();
    }

    @Override
    protected void bind(T target, FieldSet fieldSet) throws InvocationTargetException, IllegalAccessException {
        for (Field field: this.fieldList) {
            Column column = field.getAnnotation(Column.class);
            final String name;
            if (column.name().isEmpty()) {
                name = field.getName();
            }else {
                name = column.name();
            }
            desc.bindValue(target, field.getName(), fieldSet.getString(name));
        }
    }
}
