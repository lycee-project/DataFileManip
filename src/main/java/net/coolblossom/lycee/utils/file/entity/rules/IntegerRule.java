package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class IntegerRule implements DataFileRule<Integer> {

    boolean isPrimitive;

    public IntegerRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public Integer read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? 0 : null;
        } else {
            return Integer.parseInt(column);
        }
    }

    @Override
    public String write(Integer data) {
        return data.toString();
    }
}
