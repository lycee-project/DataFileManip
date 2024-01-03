package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

public class EnumRule implements DataFileRule<Object> {
    private final Class<?> type;

    public EnumRule(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object read(String column) {
        Optional<?> enumValue = Arrays.stream(type.getEnumConstants())
                .filter(e -> e.toString().equals(column))
                .findFirst();
        if (enumValue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return enumValue.get();
    }

    @Override
    public String write(Object data) {
        return data.toString();
    }
}
