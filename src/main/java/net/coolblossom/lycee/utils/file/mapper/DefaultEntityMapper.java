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
public abstract class DefaultEntityMapper<T> implements RecordMapper<T> {
    private final Class<T> target;

    protected final EntityDesc<T> desc;

    public DefaultEntityMapper(Class<T> clazz) throws NoSuchMethodException {
        this.target = clazz;
        this.desc = new EntityDesc<>(clazz);
    }

    @Override
    public T map(FieldSet fieldSet) {
        final T entity;
        try {
            entity = target.getConstructor().newInstance();
            bind(entity, fieldSet);
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // デフォルトコンストラクタが無いとき
            // 呼び出しに失敗した時
            // インスタンス生成で失敗した時
            // アクセス権限が無いとき
            throw new RecordMappingFailure(e);
        }
    }

    abstract protected void bind(T target, FieldSet fieldSet) throws InvocationTargetException, IllegalAccessException;
}
