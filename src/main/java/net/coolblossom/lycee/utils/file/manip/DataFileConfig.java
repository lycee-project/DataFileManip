package net.coolblossom.lycee.utils.file.manip;

import java.nio.charset.Charset;

/**
 * データファイルの仕様を決めるクラス
 */
public class DataFileConfig {

    static public DataFileConfig create() {
        return new DataFileConfig();
    }

    /**
     * ファイル情報
     */
    private Charset enc = Charset.defaultCharset();
    private boolean useHdrRecycle = true;
    private String lineSeparator = System.getProperty("line.separator");

    public DataFileConfig encoding(String charsetName) {
        this.enc = Charset.forName(charsetName);
        return this;
    }

    public Charset getEncoding() {
        return enc;
    }

    public DataFileConfig hasHeader(boolean use) {
        this.useHdrRecycle = use;
        return this;
    }

    public boolean hasHeader() {
        return this.useHdrRecycle;
    }

    public DataFileConfig lineSeparator(String separatorChar) {
        this.lineSeparator = separatorChar;
        return this;
    }


    /**
     * フィールド情報
     */
    private char fldSeparator = '\t';
    private char fldWrapCharacter = '\"';
    private boolean fldWrapAlways = false;

    public DataFileConfig fieldSeparator(char ch) {
        this.fldSeparator = ch;
        return this;
    }
    public char fieldSeparator() {
        return this.fldSeparator;
    }

    public DataFileConfig fieldWrap(char ch, boolean isAlways) {
        this.fldWrapCharacter = ch;
        this.fldWrapAlways = isAlways;
        return this;
    }
    public char fieldWrapChar() {
        return this.fldWrapCharacter;
    }

    public boolean isFieldWrapAlways() {
        return this.fldWrapAlways;
    }

    /**
     * レコード定義
     */
    public enum Record {
        Skip,
        Null,
        Exception
    }
    Record mode = Record.Exception;

    public DataFileConfig invalidRecord(Record mode) {
        this.mode = mode;
        return this;
    }

    public Record invalidRecord() {
        return mode;
    }
}
