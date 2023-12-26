package net.coolblossom.lycee.utils.file.entity;

/**
 * 入出力時のデータ変換定義
 * @param <T> 対象クラス
 */
public interface DataFileRule<T> {
    /**
     * 読み込みルール
     * @param column 読み込むデータ文字列
     * @return 読み込んだ時のインスタンス
     */
    T read(String column);

    /**
     * 書き出しルール
     * @param data 書き出すインスタンス
     * @return 書き出した時の文字列
     */
    String write(T data);
}
