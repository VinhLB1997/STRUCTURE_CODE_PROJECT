package webflux.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseCode {
    /**
     * 正常
     */
    public static final Integer SUCCESS = 0;

    /**
     * 認証チェックエラー
     */
    public static final Integer AUTH_CHECK_ERROR = 401;

    /**
     * パラメータチェックエラー（必須チェック、型チェックなど）
     */
    public static final Integer WEB_INPUT_ERROR = 400;

    /**
     * 論理エラー
     */
    public static final Integer SYS_LOGIC_ERROR = 400;

    /**
     * その他システムエラー
     */
    public static final Integer SYS_OTHER_ERROR = 500;
}
