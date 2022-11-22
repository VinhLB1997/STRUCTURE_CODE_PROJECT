package webflux.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConst {

    /**
     * URL API
     */
    public static final String API = "/v1/api";
    public static final String API_APP = "/v1/api/app";
    public static final String API_WEB = "/v1/api/web";
    public static final String API_WEB_ADMIN = "/v1/api/web/admin";
    public static final String API_WEB_ADMIN_TENANT = "/v1/api/web/admin/tenant";
    public static final String API_WEB_ADMIN_BRANCH = "/v1/api/web/admin/branch";
}
