package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.service.overpayment.DistrictService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/referenceBook/district")
public class DistrictControllerRest {

    private final DistrictService districtService;

    private final DistrictMapper districtMapper;

    /**
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            districtService.delete(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Найти ID
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(
            @PathVariable("id") Long id
    ) {
        try {
            return new ResponseEntity<>(districtService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Обновить
     */
    @PutMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @RequestBody DistrictDto districtDto
    ) {
        try {
            districtService.update(districtMapper.fromDto(districtDto));
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
            @RequestBody DistrictDto districtDto
    ) {
        try {
            districtService.save(districtMapper.fromDto(districtDto));
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
                    districtService.findAll(
                                    PageRequest.of(
                                            pagination,
                                            col,
                                            Sort.by("id").descending()
                                    )
                            )
                            .stream()
                            .map(districtMapper::toDto)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
