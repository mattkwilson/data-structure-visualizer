package exceptions;

public class InvaildDataStructureException extends RuntimeException {
    public InvaildDataStructureException() {
        super();
    }
    public InvaildDataStructureException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
