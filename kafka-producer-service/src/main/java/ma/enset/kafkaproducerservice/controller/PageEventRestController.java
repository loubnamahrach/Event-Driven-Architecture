package ma.enset.kafkaproducerservice.controller;

import ma.enset.kafkaproducerservice.entities.PageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class PageEventRestController {

    @Autowired
    private StreamBridge streamBridge;

    private static final String[] USERS = {"user1", "user2", "user3", "user4", "user5"};
    private final Random random = new Random();

    @GetMapping("/publish/{topic}/{name}")
    public PageEvent publish(@PathVariable String topic, @PathVariable String name) {
        PageEvent pageEvent = new PageEvent(
                name,
                USERS[random.nextInt(USERS.length)],
                new Date(),
                (long)(100 + Math.random() * 9900)
        );
        streamBridge.send(topic, pageEvent);
        return pageEvent;
    }
}
