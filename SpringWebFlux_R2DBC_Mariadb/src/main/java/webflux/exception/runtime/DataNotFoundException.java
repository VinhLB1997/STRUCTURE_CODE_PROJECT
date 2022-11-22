package webflux.exception.runtime;

/**
 * 参照及び更新対象のリソースが見つからなかった場合に送出されるクラスです。
 */
public class DataNotFoundException extends BussinessException {

    private static final long serialVersionUID = 1L;

    /**
     * このクラスのインスタンスを構築します。
     */
    public DataNotFoundException() {
        super();
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param messageList メッセージのリスト
     */
    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param cause 原因
     */
    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param messageList メッセージのリスト
     * @param cause       原因
     */
    public DataNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}
