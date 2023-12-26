package net.coolblossom.lycee.utils.file.manip;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrlfRecordReader
        implements RecordReader
{
    private static char CR = '\r';
    private static char LF = '\n';
    private static int EOF = -1;

    private BufferedReader reader;
    private DataFileConfig config;

    public CrlfRecordReader(DataFileConfig config, Path path) throws IOException {
        this.reader = Files.newBufferedReader(path, config.getEncoding());
        this.config = config;
    }

    public boolean hasNext() throws IOException {
        return this.reader.ready();
    }


    public String getRecord() throws IOException {
        StringBuilder buffer = new StringBuilder();
        int value;
        boolean inFieldWrapped = false;

        while ((value = reader.read()) != EOF) {
            char ch = (char) value;
            if (inFieldWrapped) {
                buffer.append(ch);
                if(ch == config.fieldWrapChar()) {
                    inFieldWrapped = false;
                }
            }else if (ch == CR) {
                value = reader.read();
                if (value == EOF || (char) value == LF) {
                    break;
                } else {
                    buffer.append(ch);
                    buffer.append((char) value);
                }
            } else {
                buffer.append(ch);
                if(ch == config.fieldWrapChar()) {
                    inFieldWrapped = true;
                }
            }
        }

        if (buffer.length() == 0) return null;
        return buffer.toString();
    }


    @Override
    public void close() throws Exception {
        this.reader.close();
    }
}
