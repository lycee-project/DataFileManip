package net.coolblossom.lycee.utils.file.entity.rules;

import net.coolblossom.lycee.utils.file.entity.DataFileRule;

public class CharacterRule implements DataFileRule<Character> {

    @Override
    public Character read(String column) {
        if (column == null || column.length()==0){
            return 0;
        } else {
            return column.charAt(0);
        }
    }

    @Override
    public String write(Character data) {
        return data.toString();
    }
}
