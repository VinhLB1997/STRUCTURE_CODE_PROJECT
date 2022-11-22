package webflux.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import webflux.common.Messages;
import webflux.common.ResponseCode;
import webflux.exception.runtime.AccessDeniedException;
import webflux.exception.runtime.ValidationException;
import webflux.response.common.RootResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(-2)
public class GlobalException implements ErrorWebExceptionHandler {

    private static <T> Mono<Void> writeResponse(ServerHttpResponse httpResponse, T object) {
        return httpResponse.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = httpResponse.bufferFactory();
            try {
                return bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(object));
            } catch (Exception ex) {
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return Mono.just(getRootResponse(exchange, ex)).flatMap(data -> writeResponse(exchange.getResponse(), data)
        );
    }

    private RootResponse getRootResponse(ServerWebExchange exchange, Throwable ex) {
        RootResponse rootResponse = new RootResponse();
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        if (ex instanceof WebExchangeBindException web) {
            List<String> message = new ArrayList<>();
            web.getFieldErrors().forEach(e -> message.add(e.getDefaultMessage()));
            rootResponse.setCode(ResponseCode.WEB_INPUT_ERROR);
            rootResponse.setMessage(message.get(0));
        } else if (ex instanceof AccessDeniedException) {
            rootResponse.setCode(ResponseCode.AUTH_CHECK_ERROR);
            rootResponse.setMessage(Messages.USER_NOK_AUTHORIZED);
        } else if (ex instanceof ValidationException validate) {
            rootResponse.setCode(ResponseCode.WEB_INPUT_ERROR);
            rootResponse.setMessage(validate.getErrorMessage());
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            rootResponse.setCode(ResponseCode.SYS_OTHER_ERROR);
            rootResponse.setMessage(Messages.ERROR_500_SYSTEM);
        }
        return rootResponse;
    }
}
