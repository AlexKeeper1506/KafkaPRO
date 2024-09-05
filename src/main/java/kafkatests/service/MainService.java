package kafkatests.service;

import kafkatests.dto.ConferenceDto;
import kafkatests.dto.RegisterDto;
import kafkatests.entity.Conference;
import kafkatests.exception.ConferenceExistsException;
import kafkatests.exception.ConferenceNotFound;
import kafkatests.kafka.KafkaMessageProducerService;
import kafkatests.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final ConferenceRepository repository;
    private final KafkaMessageProducerService producer;

    public MainService(KafkaMessageProducerService producer, ConferenceRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    public void createRegister(RegisterDto registerDto) {
        String name = registerDto.name();
        Long conferenceID = registerDto.conferenceID();

        if (!repository.existsByConferenceID(conferenceID)) throw new ConferenceNotFound("Conference not found");

        producer.send(name + " " + conferenceID);
    }

    public void createConference(ConferenceDto conferenceDto) {
        Long conferenceID = conferenceDto.conferenceID();
        String name = conferenceDto.name();

        if (repository.existsByConferenceID(conferenceID)) throw new ConferenceExistsException("Conference exists");

        Conference conference = new Conference();

        conference.setConferenceID(conferenceID);
        conference.setName(name);

        repository.save(conference);
    }
}
