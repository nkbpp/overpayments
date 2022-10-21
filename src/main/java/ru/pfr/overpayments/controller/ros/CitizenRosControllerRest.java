package ru.pfr.overpayments.controller.ros;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.dto.SNILSDto;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;
import ru.pfr.overpayments.model.ros.mapper.CitizenRosMapper;
import ru.pfr.overpayments.service.ros.CitizenRosService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/overpayment/ros")
public class CitizenRosControllerRest {

    private final CitizenRosMapper citizenRosMapper;
    private final CitizenRosService citizenRosService;

    @PostMapping(path ="/findPensionerBySnils", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findPensionerBySnils(@RequestBody @Valid SNILSDto snils) {
        try {
            List<CitizenRos> citizenRos = citizenRosService
                    .findPensionerBySnils(snils.getSnils());
            return new ResponseEntity<>(
                    citizenRos
                            .stream()
                            .map(citizenRosMapper::toDto)
                            .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("снилс",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path ="/findAllBySnils", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllBySnils(@RequestBody @Valid SNILSDto snils) {
        try {
            List<CitizenRos> citizenRos = citizenRosService
                    .findBySnils(snils.getSnils());
            return new ResponseEntity<>(
                    citizenRos
                            .stream()
                            .map(citizenRosMapper::toDto)
                            .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("снилс",HttpStatus.BAD_REQUEST);
        }
    }
}
