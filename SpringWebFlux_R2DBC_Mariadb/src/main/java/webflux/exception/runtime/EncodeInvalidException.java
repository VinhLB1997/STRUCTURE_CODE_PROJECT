package webflux.exception.runtime;

public class EncodeInvalidException extends RuntimeException {
    /**
     * このクラスのインスタンスを構築します。<br>
     *
     * @param cause 原因
     */
    public EncodeInvalidException(Throwable cause) {
        super(cause);
    }

}
