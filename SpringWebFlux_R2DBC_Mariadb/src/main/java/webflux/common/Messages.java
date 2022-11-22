package webflux.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String ERROR_500_SYSTEM = "System error !!!";
    public static final String USER_NOK_AUTHORIZED = "Authentication error !!!";
    public static final String DATA_NOT_FOUND = "Data not found !!!";
}
