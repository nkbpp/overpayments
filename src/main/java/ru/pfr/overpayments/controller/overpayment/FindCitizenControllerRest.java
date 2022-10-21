package ru.pfr.overpayments.controller.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.dto.FIODto;
import ru.pfr.overpayments.model.dto.NumDistrictDto;
import ru.pfr.overpayments.model.dto.SNILSDto;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.model.overpayment.mapper.PensionerMapper;
import ru.pfr.overpayments.service.overpayment.DistrictService;
import ru.pfr.overpayments.service.overpayment.PensionerService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment")
public class FindCitizenControllerRest {

    private final PensionerMapper pensionerMapper;
    private final PensionerService pensionerService;
    private final DistrictService districtService;


    @PostMapping(path ="/findPensionerSNILS", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findPensionerSNILSDto(@RequestBody @Valid SNILSDto snils,
                                                                  @RequestParam(defaultValue = "30") Integer col,
                                                                  @RequestParam(defaultValue = "1") Integer pagination,
                                                                  Model model) {
        try {
            List<Pensioner> pensioner = pensionerService
                    .findBySnils(snils.getSnils(), pagination, col);
            return new ResponseEntity<>(
                    pensioner.stream()
                    .map(pensionerMapper::toDto)
                    .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("снилс",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path ="/findPensionerDistrict")
    public ResponseEntity<?> findPensionerDistrict(@RequestBody @Valid
                                                NumDistrictDto district,
                                            @RequestParam(defaultValue = "30") Integer col,
                                            @RequestParam(defaultValue = "1") Integer pagination,
                                            Model model) {
        try {

            List<Pensioner> pensioners = pensionerService
                    .findByDistrict(districtService.findByKod(district.getNumDistrict()), pagination, col);
            return new ResponseEntity<>(
                    pensioners.stream()
                            .map(pensioner -> pensionerMapper.toDto(pensioner))
                            .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path ="/findPensionerFIO", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findPensionerFIODto(@RequestBody @Valid FIODto fio,
                                               @RequestParam(defaultValue = "30") Integer col,
                                               @RequestParam(defaultValue = "1") Integer pagination,
                                               Model model) {
        try {
            List<Pensioner> pensioners = pensionerService
                    .findByFioAndDate(fio.getSurname(), fio.getName(), fio.getPatronymic(),
                            fio.getDateOfBirth(), pagination, col);
            return new ResponseEntity<>(
                            pensioners.stream()
                            .map(pensioner -> pensionerMapper.toDto(pensioner))
                            .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path ="/findPensionerById/{id}", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findPensionerById(@PathVariable("id") Long id) {
        try {
            Pensioner pensioner = pensionerService.findById(id);
            return new ResponseEntity<>(
                    pensionerMapper.toDto(pensioner)
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Id",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path ="/findPensionerByIdRos/{id}", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findPensionerByRosId(@PathVariable("id") String id_ros) {
        try {
            Pensioner pensioner = pensionerService.findByIdRos(id_ros);
            return new ResponseEntity<>(
                    pensionerMapper.toDto(pensioner)
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Id_ros",HttpStatus.BAD_REQUEST);
        }
    }

/*    @PostMapping(path ="/findDependentSNILS", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findIjdSNILSDto(@RequestBody @Valid SNILSDto snils,
                                                 Model model) {
        try {
            List<CitizenRos> citizenRos = pensionerService
                    .findIjdBySnils(snils.getSnils());
            return new ResponseEntity<>(citizenRos.stream()
                    .map(citizen1 -> pensionerMapper.toDto(citizen1))
                    .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("снилс",HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path ="/findCarerBySnils", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCarerBySnilsDto(@RequestBody @Valid SNILSDto snils,
                                             Model model) {
        try {
            List<CitizenRos> citizenRos = pensionerService
                    .findCarerBySnils(snils.getSnils());
            return new ResponseEntity<>(citizenRos.stream()
                    .map(citizen1 -> pensionerMapper.toDto(citizen1))
                    .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("снилс",HttpStatus.BAD_REQUEST);
        }

    }

    */



}
