package webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import webflux.common.ResponseCode;
import webflux.exception.runtime.AccessDeniedException;
import webflux.exception.runtime.BussinessException;
import webflux.exception.runtime.DataNotFoundException;
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

    /**
     * Handle Exception in function (Local)
     * ( E.g Controller here )
     *
     * @param throwable e
     * @return the response entity
     */
    public Mono<ResponseEntity<RootResponse>> handleExceptionLocal(Throwable e) {
        Integer code = ResponseCode.SYS_OTHER_ERROR;
        String errorMsg = "ネットワークがビジー状態です。しばらくしてからやり直してください。";
        HttpStatus httpStatus = HttpStatus.OK;
        if (e instanceof DataNotFoundException notfound) {
            code = ResponseCode.SYS_LOGIC_ERROR;
            errorMsg = notfound.getErrorMessage();
        } else if (e instanceof BussinessException bussines) {
            errorMsg = bussines.getErrorMessage();
            code = ResponseCode.SYS_LOGIC_ERROR;
        } else if (e instanceof AccessDeniedException accessDenie) {
            errorMsg = accessDenie.getErrorMessage();
            code = ResponseCode.AUTH_CHECK_ERROR;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ResponseEntity<RootResponse> responseException = new ResponseEntity<>(
                RootResponse.builder().code(code).message(errorMsg).build(), httpStatus);
        return Mono.just(responseException);
    }
}
