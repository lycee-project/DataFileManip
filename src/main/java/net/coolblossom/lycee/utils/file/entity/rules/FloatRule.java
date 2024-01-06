package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class FloatRule implements DataFileRule<Float> {

    boolean isPrimitive;

    public FloatRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }
    @Override
    public Float read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? 0.0f : null;
        } else {
            return Float.parseFloat(column);
        }
    }

    @Override
    public String write(Float data) {
        return data.toString();
    }
}
