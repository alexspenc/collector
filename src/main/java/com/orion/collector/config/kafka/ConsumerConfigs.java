package com.orion.collector.config.kafka;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

import static io.confluent.parallelconsumer.ParallelConsumerOptions.ProcessingOrder.KEY;

@Configuration
public class ConsumerConfigs {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBrokerUrl;
    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemeRegistryUrl;
    @Value("${kafka.call-topic-create}")
    private String createCallTopicName;
    @Value("${kafka.call-topic-delete}")
    private String deleteCallTopicName;

    @Bean
    public Properties consumerFactory() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerUrl);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemeRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        return props;
    }

    public KafkaConsumer<String, SpecificRecord> kafkaConsumer() {
        return new KafkaConsumer<>(consumerFactory());
    }

    @Bean
    public ParallelStreamProcessor<String, SpecificRecord> configConsumer() {
        ParallelConsumerOptions<String, SpecificRecord> options =
            ParallelConsumerOptions.<String, SpecificRecord>builder().ordering(KEY).maxConcurrency(16).consumer(kafkaConsumer()).build();
        ParallelStreamProcessor<String, SpecificRecord> streamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options);
        streamProcessor.subscribe(List.of(createCallTopicName, deleteCallTopicName));
        return streamProcessor;
    }
}
