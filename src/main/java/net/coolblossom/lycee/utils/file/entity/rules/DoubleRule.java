package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class DoubleRule implements DataFileRule<Double> {
    boolean isPrimitive;

    public DoubleRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public Double read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? 0.0 : null;
        } else {
            return Double.parseDouble(column);
        }
    }

    @Override
    public String write(Double data) {
        return data.toString();
    }
}
