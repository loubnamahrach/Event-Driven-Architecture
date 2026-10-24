package ma.enset.kafkastreamsservice.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin("*")
public class AnalyticsController {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @GetMapping(value = "/analytics", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamAnalytics() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    ReadOnlyWindowStore<String, Long> windowStore = interactiveQueryService.getQueryableStore(
                            "page-count-store",
                            QueryableStoreTypes.windowStore()
                    );

                    Instant now = Instant.now();
                    Instant from = now.minusSeconds(5);
                    Map<String, Long> results = new HashMap<>();

                    KeyValueIterator<org.apache.kafka.streams.kstream.Windowed<String>, Long> iterator =
                            windowStore.fetchAll(from, now);

                    while (iterator.hasNext()) {
                        KeyValue<org.apache.kafka.streams.kstream.Windowed<String>, Long> next = iterator.next();
                        results.merge(next.key.key(), next.value, Long::sum);
                    }
                    iterator.close();

                    emitter.send(results, MediaType.APPLICATION_JSON);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    return;
                }
            }
        });

        return emitter;
    }
}
// SSE emitter ready
