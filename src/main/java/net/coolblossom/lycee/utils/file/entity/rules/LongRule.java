package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class LongRule implements DataFileRule<Long> {

    boolean isPrimitive;

    public LongRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public Long read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? 0L : null;
        } else {
            return Long.parseLong(column);
        }
    }

    @Override
    public String write(Long data) {
        return data.toString();
    }
}
