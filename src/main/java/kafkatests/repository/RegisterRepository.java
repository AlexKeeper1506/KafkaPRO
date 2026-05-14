package kafkatests.repository;

import kafkatests.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
    List<Register> findByConferenceID(Long conferenceID);
    void deleteAllByConferenceID(Long conferenceID);
}
