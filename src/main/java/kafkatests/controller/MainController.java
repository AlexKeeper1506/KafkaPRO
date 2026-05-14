package kafkatests.controller;

import kafkatests.dto.ConferenceDto;
import kafkatests.dto.NewRegistersDto;
import kafkatests.dto.RegisterDto;
import kafkatests.service.MainService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    private final MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @PutMapping(value = "/register")
    public void register(@RequestBody RegisterDto registerDto) {
        service.createRegister(registerDto);
    }

    @PutMapping(value = "/addConference")
    public void addConference(@RequestBody ConferenceDto conferenceDto) {
        service.createConference(conferenceDto);
    }

    @GetMapping(value = "/getNewRegisters/{conferenceID}")
    public NewRegistersDto getNewRegisters(@PathVariable Long conferenceID) {
        return service.getNewRegisters(conferenceID);
    }
}
