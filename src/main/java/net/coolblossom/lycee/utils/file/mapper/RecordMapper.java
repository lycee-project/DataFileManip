package net.coolblossom.lycee.utils.file.mapper;

/**
 * 1レコード分をクラスにマッピングするための定義
 * @param <T> 対象クラス
 */
public interface RecordMapper<T> {

    /**
     * {@link FieldSet}から対象クラスのインスタンスに変換する処理
     * @param fs 1レコード分のデータ
     * @return 変換後のインスタンス
     */
    T map(FieldSet fs);

}
