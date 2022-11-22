package webflux.exception.runtime;

/**
 * システムへのアクセスを拒否された際に送出される例外クラスです。<br>
 */
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * エラーメッセージ
     */
    private final String errorMessage;

    /**
     * このクラスのインスタンスを構築します。<br>
     */
    public AccessDeniedException() {
        super();
        this.errorMessage = null;
    }

    /**
     * このクラスのインスタンスを構築します。<br>
     *
     * @param message
     */
    public AccessDeniedException(String message) {
        super(message);
        this.errorMessage = message;
    }

    /**
     * このクラスのインスタンスを構築します。<br>
     *
     * @param cause 原因
     */
    public AccessDeniedException(Throwable cause) {
        super(cause);
        this.errorMessage = null;
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param message メッセージ
     * @param cause   原因
     */
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
