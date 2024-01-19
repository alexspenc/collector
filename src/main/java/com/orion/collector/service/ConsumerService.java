package com.orion.collector.service;

import io.confluent.parallelconsumer.ParallelStreamProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final ParallelStreamProcessor<String, SpecificRecord> streamProcessor;
    private final ConsumerHandler<SpecificRecord> consumerHandler;

    public void poll() {
        streamProcessor.poll(data -> {
            log.info("CALL: {}", data.toString());
            consumerHandler.handle(data.value());
        });
    }

}
