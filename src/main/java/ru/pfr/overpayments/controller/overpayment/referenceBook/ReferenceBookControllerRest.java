package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;

import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/referenceBook")
public class ReferenceBookControllerRest {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;

    /**
     * Удалить
     */
    @DeleteMapping("/reasonsForOverpayments/{id}")
    public ResponseEntity<?> delette(@PathVariable("id") Long id) {
        try {
            reasonsForOverpaymentsService.delete(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Найти ID
     */
    @GetMapping(path = "/reasonsForOverpayments/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(reasonsForOverpaymentsMapper.toDto(
                    reasonsForOverpaymentsService.findById(id)
            ), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновить
     */
    @PutMapping(path ="/reasonsForOverpayments",
            consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> update(
            @RequestBody ReasonsForOverpaymentsDto reasonsForOverpaymentsDto) {
        try {
            reasonsForOverpaymentsService.update(
                    reasonsForOverpaymentsMapper.fromDto(reasonsForOverpaymentsDto)
            );
            return new ResponseEntity<>("Изменено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление
     */
    @PostMapping(path ="/reasonsForOverpayments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(
            @RequestBody ReasonsForOverpaymentsDto reasonsForOverpaymentsDto) {
        try {
            reasonsForOverpaymentsService.save(
                    reasonsForOverpaymentsMapper.fromDto(reasonsForOverpaymentsDto)
            );
            return new ResponseEntity<>("Добавлено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Получить все
     */
    @PostMapping(path = "/reasonsForOverpayments/All")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "30") Integer col,
                                    @RequestParam(defaultValue = "1") Integer pagination) {
        try {
            return new ResponseEntity<>(
                    reasonsForOverpaymentsService.findAll(pagination, col)
                            .stream()
                            .map(reasonsForOverpaymentsMapper::toDto)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
