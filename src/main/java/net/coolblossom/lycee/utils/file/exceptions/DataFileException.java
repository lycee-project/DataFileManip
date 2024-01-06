package net.coolblossom.lycee.utils.file.exceptions;

public class DataFileException extends RuntimeException {

    public DataFileException() {
        super();
    }

    public DataFileException(String message) {
        super(message);
    }

    public DataFileException(Throwable t) {
        super(t);
    }

    public DataFileException(String message, Throwable t) {
        super(message, t);
    }
}
