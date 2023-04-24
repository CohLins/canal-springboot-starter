package cn.colins.canal.exception;


public class CanalException extends RuntimeException {

    public CanalException() {
    }

    public CanalException(String message) {
        super(message);
    }

    public CanalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanalException(Throwable cause) {
        super(cause);
    }
}
