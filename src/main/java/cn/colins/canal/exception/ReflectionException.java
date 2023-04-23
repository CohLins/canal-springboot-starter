package cn.colins.canal.exception;


public class ReflectionException extends Exception {
    private static final long serialVersionUID = 7642570221267566591L;

    public ReflectionException() {
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
