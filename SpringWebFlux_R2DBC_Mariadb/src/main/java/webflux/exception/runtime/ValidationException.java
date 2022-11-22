package webflux.exception.runtime;

/**
 * 検証例外クラスです。<br>
 * この例外はシステムに対するリクエスの誤りを検知した際に送出されます。
 */
public class ValidationException extends BussinessException {

    private static final long serialVersionUID = 1L;

    /**
     * このクラスのインスタンスを構築します。
     */
    public ValidationException() {
        super();
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param cause 原因
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param cause 原因
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param cause 原因
     */
    public ValidationException(Integer code, String message) {
        super(code, message);
    }

}
