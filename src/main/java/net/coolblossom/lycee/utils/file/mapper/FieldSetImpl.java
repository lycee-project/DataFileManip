package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldSetImpl implements FieldSet {
    private final List<String> dataList;
    private final Map<String, Integer> index;

    public FieldSetImpl(){
        dataList = new ArrayList<>();
        index = new HashMap<>();
    }

    public void add(String data) {
        dataList.add(data);
    }

    public void add(String name, String data) {
        index.put(name, dataList.size());
        dataList.add(data);
    }

    @Override
    public int indexSize() {
        return index.size();
    }

    @Override
    public boolean has(String columnName) {
        return index.containsKey(columnName);
    }

    @Override
    public String getString(String columnName) {
        if (!index.containsKey(columnName)) {
            throw new InvalidNameException(columnName);
        }

        return dataList.get(index.get(columnName));
    }

    @Override
    public int getInt(String columnName) {
        String value = getString(columnName);
        return Integer.parseInt(value);
    }

    @Override
    public String getStringAt(int index) {
        return dataList.get(index);
    }

    @Override
    public Integer getIntAt(int index) {
        String value = getStringAt(index);
        return Integer.parseInt(value);
    }

}
