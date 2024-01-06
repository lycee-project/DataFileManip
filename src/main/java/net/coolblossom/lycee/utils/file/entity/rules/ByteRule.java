package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class ByteRule implements DataFileRule<Byte> {

    boolean isPrimitive;

    public ByteRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }
    @Override
    public Byte read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? (byte) 0 : null;
        }
        return Byte.parseByte(column);
    }

    @Override
    public String write(Byte data) {
        return data.toString();
    }
}
