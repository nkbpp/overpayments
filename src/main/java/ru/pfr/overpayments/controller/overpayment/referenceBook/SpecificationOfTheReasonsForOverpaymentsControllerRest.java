package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/referenceBook/specificationOfTheReasonsForOverpayments")
public class SpecificationOfTheReasonsForOverpaymentsControllerRest {

    private final SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;

    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;

    /**
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            specificationOfTheReasonsForOverpaymentsService.delete(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Найти по ID
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(
            @PathVariable("id") Long id
    ) {
        try {
            return new ResponseEntity<>(specificationOfTheReasonsForOverpaymentsService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Обновить тут
     */
    @PutMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @RequestBody SpecificationOfTheReasonsForOverpaymentsDto specificationOfTheReasonsForOverpaymentsDto
    ) {
        try {
            specificationOfTheReasonsForOverpaymentsService.update(
                    specificationOfTheReasonsForOverpaymentsMapper.fromDto(
                            specificationOfTheReasonsForOverpaymentsDto
                    )
            );
            return new ResponseEntity<>("Изменено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление
     */
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(
            @RequestBody SpecificationOfTheReasonsForOverpaymentsDto specificationOfTheReasonsForOverpaymentsDto
    ) {
        try {
            specificationOfTheReasonsForOverpaymentsService.save(
                    specificationOfTheReasonsForOverpaymentsMapper.fromDto(specificationOfTheReasonsForOverpaymentsDto)
            );
            return new ResponseEntity<>("Добавлено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Получить все
     */
    @PostMapping(path = "/All")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "30") Integer col,
            @RequestParam(defaultValue = "1") Integer pagination
    ) {
        try {
            return new ResponseEntity<>(
                    specificationOfTheReasonsForOverpaymentsService.findAll(
                                    PageRequest.of(
                                            pagination,
                                            col,
                                            Sort.by("id").descending()
                                    )
                            )
                            .stream()
                            .map(specificationOfTheReasonsForOverpaymentsMapper::toDto)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
