package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class LongRule implements DataFileRule<Long> {
    @Override
    public Long read(String column) {
        if (column == null || column.length() == 0) {
            return 0L;
        } else {
            return Long.parseLong(column);
        }
    }

    @Override
    public String write(Long data) {
        return data.toString();
    }
}
