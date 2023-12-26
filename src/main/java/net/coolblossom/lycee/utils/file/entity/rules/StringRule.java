package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class StringRule implements DataFileRule<String> {
    @Override
    public String read(String column) {
        return column;
    }

    @Override
    public String write(String data) {
        return data;
    }
}
