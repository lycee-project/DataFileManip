package net.coolblossom.lycee.utils.file.manip;

import net.coolblossom.lycee.utils.file.exceptions.RecordMappingFailure;
import net.coolblossom.lycee.utils.file.mapper.DefaultEntityMapper;
import net.coolblossom.lycee.utils.file.mapper.FieldSet;
import net.coolblossom.lycee.utils.file.mapper.FieldSetImpl;
import net.coolblossom.lycee.utils.file.mapper.RecordMapper;
import net.coolblossom.lycee.utils.file.mapper.RecordMapperFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

/**
 * csvやtsvなどの構造化ファイルを読み取るクラス
 *
 * @param <T> 構造化ファイルに対応したクラス
 */
public class DataFileReader<T>
        implements AutoCloseable,
        Iterable<T> {

    static public <T> DataFileReader<T> create(
            DataFileConfig config,
            String filename,
            Class<T> clazz
    ) throws IOException, NoSuchMethodException {
        return new DataFileReader<>(config, filename, clazz);
    }

    // ファイル仕様
    private final DataFileConfig config;

    // ファイルリーダー
    private final CrlfRecordReader reader;

    // 変換処理
    private final RecordMapper<T> mapper;

    // カラム名
    private final String[] columnNames;

    public DataFileReader(DataFileConfig config, String filename, Class<T> clazz)
            throws IOException, NoSuchMethodException {
        this.config = config;

        this.mapper = mapperFactory().createMapper(clazz);

        // TODO Configの改行コードに応じて、RecordReaderを切り替える
        this.reader = new CrlfRecordReader(config, Paths.get(filename));

        if (this.config.hasHeader()) {
            String[] columns = this.reader.getRecord().split(String.valueOf(config.fieldSeparator()));
            this.columnNames = Arrays.stream(columns).map(String::trim).toArray(String[]::new);
        } else {
            this.columnNames = null;
        }
    }

    @Override
    public void close() throws Exception {
        this.reader.close();
    }

    @Override
    public Iterator<T> iterator() {
        return new DataFileReaderIterator();
    }

    private RecordMapperFactory mapperFactory() {
        return new RecordMapperFactory();
    }

    private T readOne() throws IOException, InvocationTargetException, IllegalAccessException {
        switch (config.invalidRecord()) {
            case Null:
                try {
                    return readEntity();
                } catch (RecordMappingFailure e) {
                    return null;
                }
            case Skip:
                while (reader.hasNext()) {
                    try {
                        T one = readEntity();
                        if (one != null) {
                            return one;
                        }
                    } catch (RecordMappingFailure e) {
                        // Noop
                    }
                }
                // 最後まで読み込んでしまったらnullにする
                return null;
            case Exception:
                return readEntity();
            default:
                return null;
        }
    }

    private T readEntity() throws IOException {
        if (!reader.hasNext()) {
            return null;
        }

        FieldSet record = readRecord();
        return mapper.map(record);
    }

    private FieldSet readRecord() throws IOException {

        String record = this.reader.getRecord();
        if (record == null) {
            return null;
        }

        FieldSetImpl fieldSet = new FieldSetImpl();
        StringBuilder builder = new StringBuilder();

        String wrap = String.valueOf(config.fieldWrapChar());
        boolean inWrapped = false;
        int index = 0;

        String[] fields = record.split(String.valueOf(config.fieldSeparator()));

        for (String field : fields) {
            if (inWrapped) {
                builder.append(field);
                if (field.endsWith(wrap)) {
                    inWrapped = false;
                    addField(fieldSet, index, builder.substring(1, builder.length() - 1));
                    builder = new StringBuilder();
                    index++;
                }
            } else {
                if (field.startsWith(wrap)) {
                    builder.append(field);
                    if (field.endsWith(wrap)) {
                        // 1フィールド内に区切り文字が無いとき
                        addField(fieldSet, index, builder.substring(1, builder.length() - 1));
                        builder = new StringBuilder();
                        index++;
                    } else {
                        inWrapped = true;
                    }
                } else {
                    addField(fieldSet, index, field);
                    index++;
                }
            }
        }
        return fieldSet;
    }

    private void addField(FieldSetImpl fs, int index, String value) {
        if (this.columnNames == null) {
            fs.add(value);
        } else {
            fs.add(this.columnNames[index], value);
        }
    }

    class DataFileReaderIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            try {
                return reader.hasNext();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public T next() {
            try {
                return readOne();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RecordMappingFailure(e);
            }
        }
    }
}
