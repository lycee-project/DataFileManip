package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class DoubleRule implements DataFileRule<Double> {
    @Override
    public Double read(String column) {
        if (column == null || column.length() == 0) {
            return 0.0;
        } else {
            return Double.parseDouble(column);
        }
    }

    @Override
    public String write(Double data) {
        return data.toString();
    }
}
