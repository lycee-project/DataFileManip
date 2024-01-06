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
    private Charset encoding = Charset.defaultCharset();
    private boolean hasHeader = true;
    private String lineSeparator = System.getProperty("line.separator");

    public DataFileConfig encoding(String charsetName) {
        this.encoding = Charset.forName(charsetName);
        return this;
    }

    public Charset encoding() {
        return encoding;
    }

    public DataFileConfig hasHeader(boolean use) {
        this.hasHeader = use;
        return this;
    }

    public boolean hasHeader() {
        return this.hasHeader;
    }

    public DataFileConfig lineSeparator(String separatorChar) {
        this.lineSeparator = separatorChar;
        return this;
    }


    /**
     * フィールド情報
     */
    private char fieldSeparator = '\t';
    private char fieldWrapCharacter = '\"';
    private boolean fieldWrapAlways = false;

    public DataFileConfig fieldSeparator(char ch) {
        this.fieldSeparator = ch;
        return this;
    }
    public char fieldSeparator() {
        return this.fieldSeparator;
    }

    public DataFileConfig fieldWrap(char ch, boolean isAlways) {
        this.fieldWrapCharacter = ch;
        this.fieldWrapAlways = isAlways;
        return this;
    }
    public char fieldWrapChar() {
        return this.fieldWrapCharacter;
    }

    public boolean fieldWrapAlways() {
        return this.fieldWrapAlways;
    }

    /**
     * レコード定義
     */
    public enum Record {
        Skip,
        Null,
        Exception
    }
    private Record mode = Record.Exception;

    public DataFileConfig invalidRecord(Record mode) {
        this.mode = mode;
        return this;
    }

    public Record invalidRecord() {
        return mode;
    }
}
