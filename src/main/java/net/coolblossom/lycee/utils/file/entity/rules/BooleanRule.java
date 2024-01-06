package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class BooleanRule implements DataFileRule<Boolean> {
    boolean isPrimitive;

    public BooleanRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }
    @Override
    public Boolean read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? true : null;
        } else {
            return Boolean.parseBoolean(column);
        }
    }

    @Override
    public String write(Boolean data) {
        return data.toString();
    }
}
