package net.coolblossom.lycee.utils.file.mapper;

import net.coolblossom.lycee.utils.file.entity.Column;
import net.coolblossom.lycee.utils.file.entity.EntityDesc;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * アノテーションを解析して対象クラスにマッピングするクラス
 *
 * @param <T> 対象クラス
 */
public class DefaultEntityMapper<T> implements RecordMapper<T> {
    private final Class<T> target;

    private final EntityDesc desc;

    public DefaultEntityMapper(Class<T> clazz) throws NoSuchMethodException {
        this.target = clazz;
        this.desc = new EntityDesc(clazz);
    }

    @Override
    public T map(FieldSet fs) {
        T entity;
        try {
            entity = target.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // デフォルトコンストラクタが無いとき
            // 呼び出しに失敗した時
            // インスタンス生成で失敗した時
            // アクセス権限が無いとき
            throw new RecordMappingFailure(e);
        }

        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);

            final String name;
            if (col != null && col.name().length() > 0) {
                name = col.name();
            } else {
                name = field.getName();
            }

            if (!fs.has(name)) {
                continue;
            }
            try {
                if (field.getType().equals(String.class)) {
                    field.set(entity, fs.getString(name));
                } else if (field.getType().equals(int.class)) {
                    field.setInt(entity, fs.getInt(name));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


        return entity;
    }
}
