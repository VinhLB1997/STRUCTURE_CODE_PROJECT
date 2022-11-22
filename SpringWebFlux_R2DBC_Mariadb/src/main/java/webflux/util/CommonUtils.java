package webflux.util;

public class CommonUtils {

    /**
     *
     * 値の存在可否をチェック.<br>
     *
     * @param value チェックする値
     * @return 値がnullまたは空文字列の場合にtrueを返却
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
