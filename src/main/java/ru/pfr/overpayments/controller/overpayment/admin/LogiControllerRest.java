package ru.pfr.overpayments.controller.overpayment.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.dto.AppError;
import ru.pfr.overpayments.model.overpayment.dto.log.LogiDto;
import ru.pfr.overpayments.model.overpayment.mapper.log.LogiMapper;
import ru.pfr.overpayments.service.overpayment.log.LogiService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/log")
public class LogiControllerRest {

    private final LogiService logiService;

    private final LogiMapper logiMapper;

    /**
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            logiService.deleteById(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удалить
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(
    ) {
        try {
            var size = logiService.findAll().size();
            logiService.clear();
            return new ResponseEntity<>("Было удалено " + size + " записей!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AppError(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()
                    ),
                    HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(logiService.findById(id), HttpStatus.OK);
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
            @RequestBody LogiDto logiDto
    ) {
        try {
            logiService.save(logiMapper.fromDto(logiDto));
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
            @RequestBody LogiDto logiDto
    ) {
        try {
            logiService.save(logiMapper.fromDto(logiDto));
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
                    logiService.findAll()
                            .stream()
                            .map(logiMapper::toDto)
                            .skip((long) col * (pagination - 1))
                            .limit(col)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
