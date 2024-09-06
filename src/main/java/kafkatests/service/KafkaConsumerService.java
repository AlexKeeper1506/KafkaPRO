package kafkatests.service;

import kafkatests.dto.NewRegistersDto;
import kafkatests.entity.Register;
import kafkatests.repository.RegisterRepository;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KafkaConsumerService {
    Consumer<String, String> consumer;
    RegisterRepository repository;

    public KafkaConsumerService(ConsumerFactory<String, String> consumerFactory, RegisterRepository repository) {
        consumer = consumerFactory.createConsumer("conference", "reader");
        consumer.subscribe(Arrays.asList("topic1"));
        this.repository = repository;
    }

    public NewRegistersDto getNewRegisters(Long conferenceId) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

        for (ConsumerRecord<String, String> record : records) {
            String str = record.value();
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
