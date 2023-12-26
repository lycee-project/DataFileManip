package net.coolblossom.lycee.utils.file.manip;

import java.io.IOException;

public interface RecordReader extends AutoCloseable {
    String getRecord() throws IOException;
}
