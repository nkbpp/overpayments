package ru.pfr.overpayments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.OverpaymentDto;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;
import ru.pfr.overpayments.model.overpayment.mapper.OverpaymentMapper;
import ru.pfr.overpayments.service.overpayment.OverpaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/informationOverpayment")
public class InformationOverpaymentsControllerRest {

    private final OverpaymentService overpaymentService;
    private final OverpaymentMapper overpaymentMapper;

    /**
     * Найти IDIS
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByIsId(
            @PathVariable("id") Long isId
    ) {
        try {
            Overpayment overpayment = overpaymentService.findByIsId(isId);
            if (overpayment == null) {
                return new ResponseEntity<>("", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        overpaymentMapper.toDto(overpayment)
                        , HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление
     */
    @PostMapping(path = "", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(
            @RequestBody OverpaymentDto overpaymentDto //@Valid
    ) {
        try {
            if (overpaymentService.findByIsId(overpaymentDto.getIdOverpayment()) != null) { //уже есть такая запись
                return new ResponseEntity<>("Такая переплата уже добавлена", HttpStatus.BAD_REQUEST);
            }
            overpaymentService.save(overpaymentMapper.fromDto(overpaymentDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновление
     */
    @PutMapping(path = "/{id}", //produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @PathVariable("id") Long idis,
            @RequestBody OverpaymentDto overpaymentDto //@Valid
    ) {
        try {
            var newOverpayment = overpaymentMapper.fromDto(overpaymentDto);
            Overpayment oldOverpayment = overpaymentService.findByIsId(idis);
            newOverpayment.setId(oldOverpayment.getId());

            overpaymentService.update(newOverpayment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
