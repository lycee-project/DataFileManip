package net.coolblossom.lycee.utils.file.entity;

import net.coolblossom.lycee.utils.file.entity.bind.DirectFieldBinder;
import net.coolblossom.lycee.utils.file.entity.bind.FieldBinder;
import net.coolblossom.lycee.utils.file.entity.bind.SetterBinder;
import net.coolblossom.lycee.utils.file.entity.rules.*;
import net.coolblossom.lycee.utils.file.exceptions.DataFileException;
import net.coolblossom.lycee.utils.file.exceptions.InvalidRecordException;
import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;
import net.coolblossom.lycee.utils.file.mapper.FieldSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * 対象クラスのフィールドにバインドする情報を保持したクラス
 * <p>
 *     {@link Column}に設定された情報をもとにバインドを行う。
 *     {@link Column#order()}よりも{@link Column#name()}を優先する。
 *     {@link Column#order()}を利用する際はすべてに設定されていること。
 * </p>
 */
public class EntityDesc<T> {


    // クラス情報を登録しておいて
    // カラム名と値を渡すと，対応したBeanフィールドに設定してくれる
    // その際に，きちんとRuleが適用されていること
    //
    // ex)
    // name="alice"を渡したら，Info#nameに"alice"と登録され，
    // age="19"を渡したら，Info#ageに19と登録される
    //
    private final Map<String, FieldBinder> binderList;

    public EntityDesc(Class<T> clazz) throws NoSuchMethodException {
        binderList = new HashMap<>();

        // フィールドを検査
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                // Columnが付いてなかったら対象外
                continue;
            }

            // フィールドに値を設定するバインダーを決定する
            FieldBinder binder;
            Class<?> type = field.getType();
            Column annotation = field.getAnnotation(Column.class);

            DataFileRule<?> rule;
            if (annotation.rule().length > 0) {
                // ルール有り
                rule = createRule(annotation.rule()[0]);
            } else if (type.equals(char.class) || type.equals(Character.class)) {
                rule = new CharacterRule(type.isPrimitive());
            } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                rule = new ByteRule(type.isPrimitive());
            } else if (type.equals(short.class) || type.equals(Short.class)) {
                rule = new ShortRule(type.isPrimitive());
            } else if (type.equals(int.class) || type.equals(Integer.class)) {
                rule = new IntegerRule(type.isPrimitive());
            } else if (type.equals(long.class) || type.equals(Long.class)) {
                rule = new LongRule(type.isPrimitive());
            } else if (type.equals(float.class) || type.equals(Float.class)) {
                rule = new FloatRule(type.isPrimitive());
            } else if (type.equals(double.class) || type.equals(Double.class)) {
                rule = new DoubleRule(type.isPrimitive());
            } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                rule = new BooleanRule(type.isPrimitive());
            } else if (type.isEnum()) {
                rule = new EnumRule(type);
            } else if (type.equals(String.class)) {
                rule = new StringRule();
            } else {
                throw new RecordMappingFailure(String.format("対応する型ではありません[%s]", type.getCanonicalName()));
            }

            String fieldName = field.getName();

            if (Modifier.isPublic(field.getModifiers())) {
                // フィールドに直接設定出来るとき
                binder = new DirectFieldBinder(field, rule);
            } else {
                // メソッド経由で設定するとき
                String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method setterMethod;
                try {
                    setterMethod = clazz.getDeclaredMethod(setterName, type);
                } catch (NoSuchMethodException e) {
                    throw new RecordMappingFailure(String.format("対応するメソッドがありません[%s]", fieldName));
                }
                if (!Modifier.isPublic(setterMethod.getModifiers())) {
                    throw new RecordMappingFailure(String.format("対応するメソッドがありません[%s]", fieldName));
                }
                binder = new SetterBinder(setterMethod, rule);
            }
            this.binderList.put(fieldName, binder);
        }

    }

    public void bindValue(T entity, String name, String value)
            throws InvocationTargetException, IllegalAccessException, InvalidRecordException {
        if(!binderList.containsKey(name)) {
            return;
        }

        FieldBinder binder = binderList.get(name);
        try {
            binder.bind(entity, value);
        } catch (InvocationTargetException | IllegalAccessException | InvalidRecordException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidRecordException(e);
        }
    }

    /**
     * アノテーションに設定されていたルールクラスを生成
     *
     * @param ruleClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    private DataFileRule<?> createRule(Class<? extends DataFileRule> ruleClass) {
        try {
            return ruleClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RecordMappingFailure(e);
        }
    }

}
