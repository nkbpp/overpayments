package ru.pfr.overpayments.controller.overpayment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.service.overpayment.PensionerService;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/overpayment/pensioner")
public class AddPensionerControllerRest {

    private final PensionerService pensionerService;

    /**
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            pensionerService.delete(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
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
            @RequestBody @Valid PensionerDto pensionerDto
    ) {
        try {
            if (pensionerService.findByIdRos(pensionerDto.getId_ros()) != null) { //уже есть такая запись
                return new ResponseEntity<>("Такой пенсионер уже добавлен", HttpStatus.BAD_REQUEST);
            }
            pensionerService.save(pensionerDto);
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
            @PathVariable("id") Long id,
            @RequestBody @Valid PensionerDto addPensionerDto
    ) {
        try {
            Pensioner pensioner = pensionerService.findById(id);
            pensioner.setAdrreg(addPensionerDto.getAdrreg());
            pensioner.setTel(addPensionerDto.getTel());
            pensionerService.save(pensioner);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
