package net.coolblossom.lycee.utils.file.exceptions;

public class RecordMappingFailure extends DataFileException {

    public RecordMappingFailure(String message) {
        super(message);
    }

    public RecordMappingFailure(Throwable e) {
        super(e);
    }

    public RecordMappingFailure(String message, Throwable e) {
        super(message, e);
    }
}
