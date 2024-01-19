package com.orion.collector.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

@Service
@RequiredArgsConstructor
public class AvroScheduleConsumer {

    private final ConsumerService consumerService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void runScheduler() {
        scheduler.scheduleAtFixedRate(consumerService::poll, 10, 10, MICROSECONDS);
    }

    @PreDestroy
    private void shutdown() {
        scheduler.shutdown();
    }
}
