package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sse")
@LogRecord(enabled = false)
@Tag(name = "sse模块")
public class SSEController {

    private Disposable subscribe;

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamEvents() {

        Flux<ServerSentEvent<String>> flux = Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("custom-event")
                        .data("Event data: " + LocalDateTime.now())
                        .build())
                .takeUntil(t -> t.id().length() > 1);
        subscribe = flux.subscribe();
        return flux;
    }

    @GetMapping(path = "/streamBlock", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String streamEvents1() {

        Flux<ServerSentEvent<String>> flux = Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("custom-event")
                        .data("Event data: " + LocalDateTime.now())
                        .build())
                .takeUntil(t -> t.id().length() > 1);
        Mono<String> mono = flux.map(c -> c.data()).collect(Collectors.joining(", "));
        return mono.block();
    }

    @GetMapping(path = "/multi", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String multiStream() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Flux<ServerSentEvent<String>> flux = Flux.fromIterable(list)
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("custom-event")
                        .data("Event data: " + LocalDateTime.now())
                        .build())
                .takeUntil(t -> t.id().length() > 1);

        Mono<String> mono = flux.map(c -> c.data()).collect(Collectors.joining(", "));
        return mono.block();
    }

    @GetMapping(path = "/stop")
    public void stop() {
        subscribe.dispose();
    }


    @GetMapping("/emitter")
    public ResponseBodyEmitter streamData() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // 异步执行推送数据
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    emitter.send("消息 " + i + " - 时间：" + LocalTime.now() + "<br/>");
                    Thread.sleep(1000); // 模拟数据生成延迟
                }
                emitter.complete(); // 结束流
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @GetMapping(value = "/streamingResponseBody", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StreamingResponseBody streamFile() {
        return outputStream -> {
            for (int i = 0; i < 1000; i++) {
                outputStream.write(("seq " + i + "\n").getBytes(Charset.defaultCharset())
                );
                outputStream.flush();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (i >= 20) {
                    outputStream.close();
                    break;
                }
            }
        };
    }
}
