package kafkatests.repository;

import kafkatests.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    boolean existsByConferenceID(Long conferenceID);
}
