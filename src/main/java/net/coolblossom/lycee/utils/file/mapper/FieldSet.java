package net.coolblossom.lycee.utils.file.mapper;

/**
 * 1レコード分のデータセット
 */
public interface FieldSet {
    /**
     * 存在確認
     * @param columnName カラム名
     * @return ある場合trueを返却する
     */
    boolean has(String columnName);

    /**
     * カラムの取得
     * @param columnName カラム名
     * @return 該当カラムの文字列
     */
    String getString(String columnName);

    /**
     * カラムの取得
     * @param columnName カラム名
     * @return 該当カラムの数値
     */
    int getInt(String columnName);

}
