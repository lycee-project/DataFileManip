package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class FloatRule implements DataFileRule<Float> {
    @Override
    public Float read(String column) {
        if (column == null || column.length() == 0) {
            return 0.0f;
        } else {
            return Float.parseFloat(column);
        }
    }

    @Override
    public String write(Float data) {
        return data.toString();
    }
}
