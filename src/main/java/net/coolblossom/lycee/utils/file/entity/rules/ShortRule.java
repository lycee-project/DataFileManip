package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class ShortRule implements DataFileRule<Short> {

    boolean isPrimitive;

    public ShortRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }
    @Override
    public Short read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? (short) 0 : null;
        } else {
            return Short.parseShort(column);
        }
    }

    @Override
    public String write(Short data) {
        return data.toString();
    }
}
