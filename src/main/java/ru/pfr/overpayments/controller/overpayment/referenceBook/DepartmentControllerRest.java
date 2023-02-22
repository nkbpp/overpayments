package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.department.DepartmentRequest;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.department.DepartmentResponse;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.DepartmentRequestMapper;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.DepartmentResponseMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/referenceBook/department")
public class DepartmentControllerRest {

    private final DepartmentService departmentService;
    private final DepartmentResponseMapper departmentResponseMapper;
    private final DepartmentRequestMapper departmentRequestMapper;

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
            @RequestBody DepartmentRequest departmentRequest
    ) {
        try {
            departmentService.update(departmentRequestMapper.apply(departmentRequest).get());
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
            @RequestBody DepartmentRequest departmentRequest
    ) {
        try {
            departmentService.save(departmentRequestMapper.apply(departmentRequest).get());
            return new ResponseEntity<>("Добавлено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получить все
     */
    @PostMapping(path = "/All")
    public ResponseEntity<List<DepartmentResponse>> getAll(
            @RequestParam(defaultValue = "30") Integer col,
            @RequestParam(defaultValue = "1") Integer pagination
    ) {
        try {
            return new ResponseEntity<>(
                    departmentService.findAll(
                                    PageRequest.of(
                                            pagination,
                                            col,
                                            Sort.by("id").descending()
                                    )
                            )
                            .stream()
                            .map(departmentResponseMapper)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
