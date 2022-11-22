package webflux.log.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

public class BodyCaptureRequest extends ServerHttpRequestDecorator {

    private final StringBuilder body = new StringBuilder();

    public BodyCaptureRequest(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::capture);
    }

    private void capture(DataBuffer buffer) {
        this.body.append(StandardCharsets.UTF_8.decode(buffer.asByteBuffer()).toString());
    }

    @Override
    public MultiValueMap<String, String> getQueryParams() {
        return super.getQueryParams();
    }

    public String getFullBody() {
        return this.body.toString();
    }

    public String getBodyQueryParam() {
        MultiValueMap<String, String> params = getQueryParams();
        Map<String, Object> requestValue = new HashMap<>();
        params.forEach((name, values) -> values.forEach(value -> {
            requestValue.put(name, values.size() == 1 ? values.get(0) : values);
        }));
        try {
            return new ObjectMapper().writeValueAsString(requestValue);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

}
