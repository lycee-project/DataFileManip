package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.entity.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class OrderedAccessMapper<T> extends DefaultEntityMapper<T> {

    private final List<Field> fieldList;

    public OrderedAccessMapper(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);

        this.fieldList = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .toList();
    }

    @Override
    protected void bind(T entity, FieldSet fieldSet) throws InvocationTargetException, IllegalAccessException {
        int indexSize = fieldSet.indexSize();
        for (Field field: this.fieldList) {
            int index = field.getAnnotation(Column.class).order() - 1;
            if (index >= indexSize) {
                // orderが項目数を超えている場合、値の設定は行わない
                continue;
            }
            desc.bindValue(entity, field.getName(), fieldSet.getStringAt(index));
        }
    }
}
