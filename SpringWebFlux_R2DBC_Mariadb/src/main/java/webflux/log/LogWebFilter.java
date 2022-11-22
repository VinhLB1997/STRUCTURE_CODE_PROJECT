package webflux.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.MarkerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import webflux.common.WebConst;
import webflux.log.common.BodyCaptureExchange;
import webflux.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogWebFilter implements WebFilter {

    private final String USER_FIXED = "Vinhnt";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        BodyCaptureExchange bodyCaptureExchange = new BodyCaptureExchange(exchange);
        String requestId = getRequestId(exchange.getRequest().getHeaders());
        String requestUri = exchange.getRequest().getURI().getPath().toString();
        return chain.filter(bodyCaptureExchange).doOnEach(logOnEach(r -> {
            String requestBody = bodyCaptureExchange.getRequest().getFullBody();
            if (CommonUtils.isEmpty(requestBody)) {
                requestBody = bodyCaptureExchange.getRequest().getBodyQueryParam();
            }
            String responseBody = bodyCaptureExchange.getResponse().getFullBody();
            if (requestUri.contains(WebConst.API)) {
                log.info(MarkerFactory.getMarker("REQUEST"), "{}", requestBody);
                if (responseBody != null) {
                    log.info(MarkerFactory.getMarker("RESPONSE"), "{}", responseBody);
                }
            }
        })).contextWrite(ctx -> {
            Map<String, String> dataContext = new HashMap<String, String>();
            dataContext.put("CONTEXT_KEY", requestId);
            dataContext.put("USER_X", USER_FIXED);
            dataContext.put("REQUEST_URI", requestUri);
            ContextView context = Context.of(dataContext);
            exchange.getAttributes().put("DATA_CONTEXT_ATTRIBUTE", context);
            return ctx.putAll(context);
        });

    }

    private String getRequestId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get("X-Request-ID");
        return requestIdHeaders == null || requestIdHeaders.isEmpty() ? UUID.randomUUID().toString()
                : requestIdHeaders.get(0);
    }

    public static <T> Consumer<Signal<T>> logOnEach(Consumer<T> logStatement) {
        return signal -> {
            String contextValue = signal.getContextView().get("CONTEXT_KEY");
            String userValue = signal.getContextView().get("USER_X");
            String uriValue = signal.getContextView().get("REQUEST_URI");
            try (MDC.MDCCloseable cMdcOne = MDC.putCloseable("MDC_KEY", contextValue);
                 MDC.MDCCloseable cMdcTwo = MDC.putCloseable("USER_X", userValue);
                 MDC.MDCCloseable cMdcThree = MDC.putCloseable("REQUEST_URI", uriValue)) {
                logStatement.accept(signal.get());
            }
        };
    }
}
