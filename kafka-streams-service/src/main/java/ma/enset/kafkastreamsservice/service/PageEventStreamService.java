package ma.enset.kafkastreamsservice.service;

import ma.enset.kafkastreamsservice.entities.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Service
public class PageEventStreamService {

    @Bean
    public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
        return (input) -> {
            return input
                    .filter((k, v) -> v.getDuration() > 100)
                    .map((k, v) -> new KeyValue<>(v.getName(), 0L))
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
                    .count(Materialized.as("page-count-store"))
                    .toStream()
                    .map((k, v) -> new KeyValue<>(k.key(), v));
        };
    }
}
// KStream windowed aggregation
