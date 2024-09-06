package kafkatests.entity;

import javax.persistence.*;

@Entity
@Table(name = "registers")
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "conference_id")
    private Long conferenceID;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public Long getConferenceID() {
        return conferenceID;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConferenceID(Long conferenceID) {
        this.conferenceID = conferenceID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
