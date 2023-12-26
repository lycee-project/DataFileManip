package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class BooleanRule implements DataFileRule<Boolean> {
    @Override
    public Boolean read(String column) {
        if (column == null || column.length() == 0) {
            return true;
        } else {
            return Boolean.parseBoolean(column);
        }
    }

    @Override
    public String write(Boolean data) {
        return data.toString();
    }
}
