package ru.pfr.overpayments.controller.overpayment.referenceBook;

import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.overpayments.model.overpayment.entity.Documents;
import ru.pfr.overpayments.model.overpayment.mapper.DocumentsMapper;
import ru.pfr.overpayments.service.overpayment.DocumentsService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/documents")
public class DocumentsControllerRest {

    private final DocumentsService documentsService;
    private final DocumentsMapper documentsMapper;

    /***
     * Удалить
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            documentsService.delete(id);
            return new ResponseEntity<>(
                    "Удаление прошло успешно!",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Добавление
     */
    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(
            @RequestParam("nameFile") String nameFile,
            @RequestPart MultipartFile dokument
    ) {
        try {
            nameFile = nameFile.equals("") ? dokument.getName() : nameFile;
            nameFile = nameFile.lastIndexOf('.') != -1 ? nameFile : nameFile + ".docx";

            documentsService.save(Documents
                    .builder()
                    .nameFile(nameFile)
                    .dokument(dokument.getBytes())
                    .build()
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Обновление
     */
    @PutMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(
            @RequestParam("id") Long id,
            @RequestParam("nameFile") String nameFile,
            @RequestPart(name = "dokument", required = false) MultipartFile dokument
    ) {
        try {
            Documents documents = documentsService.findById(id);
            nameFile = nameFile.equals("") ? dokument.getName() : nameFile;
            nameFile = nameFile.lastIndexOf('.') != -1 ? nameFile : nameFile + ".docx";
            documents.setNameFile(nameFile);
            if (dokument != null) {
                documents.setDokument(dokument.getBytes());
            }
            documentsService.save(documents);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Получение
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> get(
            @PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(
                    documentsMapper.toDto(documentsService.findById(id)),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Получить все
     */
    @PostMapping(path = "/All")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "30") Integer col,
                                    @RequestParam(defaultValue = "1") Integer pagination) {
        try {
            return new ResponseEntity<>(
                    documentsService.findAll(pagination, col)
                            .stream()
                            .map(documentsMapper::toDto)
                            .collect(Collectors.toList()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Получение документа по id
     */
    @GetMapping(path = "/download/{id}")
    public @ResponseBody
    ResponseEntity<?> download(
            @PathVariable("id") Long id
    ) {
        Documents document = documentsService.findById(id);

        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                toLatinTrans.transliterate(document.getNameFile()
                                        .replaceAll("ё", "е").replaceAll("Ё", "Е")
                                ) + "\"")
                .body(document.getDokument());
    }

}
