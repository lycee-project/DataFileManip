package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class ShortRule implements DataFileRule<Short> {

    @Override
    public Short read(String column) {
        if (column == null || column.length() == 0) {
            return 0;
        } else {
            return Short.parseShort(column);
        }
    }

    @Override
    public String write(Short data) {
        return data.toString();
    }
}
