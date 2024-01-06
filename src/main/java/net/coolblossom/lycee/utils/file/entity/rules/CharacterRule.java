package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class CharacterRule implements DataFileRule<Character> {

    boolean isPrimitive;

    public CharacterRule(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }
    @Override
    public Character read(String column) {
        if (column == null || column.isEmpty()) {
            return this.isPrimitive ? (char) 0 : null;
        } else {
            return column.charAt(0);
        }
    }

    @Override
    public String write(Character data) {
        return data.toString();
    }
}
