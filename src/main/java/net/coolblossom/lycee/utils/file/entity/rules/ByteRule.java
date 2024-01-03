package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class ByteRule implements DataFileRule<Byte> {

    @Override
    public Byte read(String column) {
        if (column == null || column.isEmpty()) {
            return 0;
        }
        return Byte.parseByte(column);
    }

    @Override
    public String write(Byte data) {
        return data.toString();
    }
}
