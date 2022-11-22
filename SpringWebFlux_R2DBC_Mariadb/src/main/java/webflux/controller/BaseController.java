package webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import webflux.common.ResponseCode;
import webflux.response.common.ResponseBody;
import webflux.response.common.RootResponse;

@Controller
public class BaseController {

    /**
     * Tạo kết quả trả về.
     *
     * @param body the body
     * @return the response entity
     */
    public ResponseEntity<RootResponse> success(ResponseBody body) {
        return new ResponseEntity<>(RootResponse.builder().payload(body).code(ResponseCode.SUCCESS).build(),
                HttpStatus.OK);
    }
}
