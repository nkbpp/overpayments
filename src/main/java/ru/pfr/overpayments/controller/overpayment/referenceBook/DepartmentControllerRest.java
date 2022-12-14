package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.DepartmentDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.DepartmentMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/referenceBook/department")
public class DepartmentControllerRest {

    private final DepartmentService departmentService;

    private final DepartmentMapper departmentMapper;

    /**
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            departmentService.delete(id);
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
            return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
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
            @RequestBody DepartmentDto departmentDto
    ) {
        try {
            departmentService.update(departmentMapper.fromDto(departmentDto));
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
            @RequestBody DepartmentDto departmentDto
    ) {
        try {
            departmentService.save(departmentMapper.fromDto(departmentDto));
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
                    departmentService.findAll()
                            .stream()
                            .map(departmentMapper::toDto)
                            .skip((long) col * (pagination - 1))
                            .limit(col)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
