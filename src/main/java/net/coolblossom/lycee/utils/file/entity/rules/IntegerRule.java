package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class IntegerRule implements DataFileRule<Integer> {
    @Override
    public Integer read(String column) {
        if (column == null || column.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(column);
        }
    }

    @Override
    public String write(Integer data) {
        return data.toString();
    }
}
