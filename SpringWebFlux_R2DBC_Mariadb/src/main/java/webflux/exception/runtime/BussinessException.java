package webflux.exception.runtime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ビジネス例外クラスです。
 * <p>
 * この例外は誤りを訂正することで処理継続が可能なエラーが生じた場合に送出されます。
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BussinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * レスポンスコード
     */
    private final Integer responseCode;

    /**
     * エラーメッセージ
     */
    private final String errorMessage;

    /**
     * このクラスのインスタンスを構築します。
     */
    public BussinessException() {
        super();
        this.errorMessage = null;
        this.responseCode = null;
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param responseCode レスポンスコード
     * @param errorMessage エラーメッセージ
     */
    public BussinessException(Integer responseCode, String errorMessage) {
        super();
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param messageList メッセージのリスト
     */
    public BussinessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.responseCode = null;
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param cause 原因
     */
    public BussinessException(Throwable cause) {
        super(cause);
        this.errorMessage = null;
        this.responseCode = null;
    }

    /**
     * このクラスのインスタンスを構築します。
     *
     * @param messageList メッセージのリスト
     * @param cause       原因
     */
    public BussinessException(String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
        this.responseCode = null;
    }
}
