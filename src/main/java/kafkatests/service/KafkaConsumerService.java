package kafkatests.service;

import kafkatests.dto.NewRegistersDto;
import kafkatests.entity.Register;
import kafkatests.repository.RegisterRepository;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final Consumer<String, String> consumer;
    private final RegisterRepository repository;

    public KafkaConsumerService(RegisterRepository repository) {
        Properties consumerProperties = new Properties();

        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("topic1"));

        this.repository = repository;
    }

    public NewRegistersDto getNewRegisters(Long conferenceId) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

        for (ConsumerRecord<String, String> record : records) {
            String str = record.value();
            LOG.info(str);
            String[] stringArr = str.split(" ");

            Register register = new Register();

            register.setConferenceID(Long.parseLong(stringArr[0]));
            register.setName(stringArr[1]);

            repository.save(register);
        }

        List<Register> newRegisters = repository.findByConferenceID(conferenceId);
        List<String> resultList = new ArrayList<>();

        for (Register register : newRegisters) {
            resultList.add(register.getName());
        }

        NewRegistersDto responseDto = new NewRegistersDto(resultList);

        repository.deleteAllByConferenceID(conferenceId);

        return responseDto;
    }
}
