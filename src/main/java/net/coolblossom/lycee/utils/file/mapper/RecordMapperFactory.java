package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.entity.Column;
import net.coolblossom.lycee.utils.file.exceptions.DataFileException;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class RecordMapperFactory {
    public <T> RecordMapper<T> createMapper(Class<T> target) throws NoSuchMethodException {
        if (checkUseOrder(target)) {
            // Column#orderでマッピングする
            return new OrderedAccessMapper<>(target);
        } else {
            // Column#nameでマッピングする
            return new NamedAccessMapper<>(target);
        }
    }

    private boolean checkUseOrder(Class<?> clazz) {
        Set<Integer> orders = new HashSet<>();
        int orderedColumnCount = 0;

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            orderedColumnCount += 1;
            int order = field.getAnnotation(Column.class).order();

            if (order == 0) {
                // デフォルト値は判定しない
                continue;
            }

            if (order < 0) {
                // 負数は認めない
                throw new RecordMappingFailure(
                        String.format("%s#%sについているColumnのorderが1以上の値ではありません",
                                clazz.getCanonicalName(),
                                field.getName())
                );
            }

            if (!orders.add(order)) {
                // orderが重複している
                throw new RecordMappingFailure(
                        String.format("%s#%sについているColumnのorderが重複しています",
                                clazz.getCanonicalName(),
                                field.getName())
                );
            }
        }

        if (orders.isEmpty()) {
            // orderを使ってない
            return false;
        }

        if (orders.size() != orderedColumnCount) {
            throw new DataFileException("すべてのColumnにorderが指定されていません");
        }

        return true;
    }

}
