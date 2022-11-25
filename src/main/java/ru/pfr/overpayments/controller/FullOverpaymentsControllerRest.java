package ru.pfr.overpayments.controller;

import com.github.petrovich4j.Case;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.mapper.FullOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.entity.Citizen;
import ru.pfr.overpayments.model.overpayment.entity.Documents;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.service.FullOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.DocumentsService;
import ru.pfr.overpayments.service.overpayment.PensionerService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overpayment/fullInformationOverpayment")
public class FullOverpaymentsControllerRest {
    private final FullOverpaymentsMapper fullOverpaymentsMapper;
    private final FullOverpaymentsService fullOverpaymentsService;
    private final DocumentsService documentsService;
    private final PensionerService pensionerService;

    /**
     * Найти ID
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByIsId(
            @PathVariable("id") String id
    ) {
        try {
            return new ResponseEntity<>(
                    fullOverpaymentsService.findById(id).stream()
                            .map(fullOverpaymentsMapper::toDto)
                            .collect(Collectors.toList())
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * скачать документ по шаблону
     */
    @GetMapping(path = "/download")
    public ResponseEntity<?> downloadPensioner(
            @RequestParam("isId") Long idIs,
            @RequestParam(name = "who", defaultValue = "") String who
    ) {
        try {
            var fullOverpayment = fullOverpaymentsService.findByIdIs(idIs);
            Documents document = who.equals("carer") ?
                    documentsService.findById(fullOverpayment
                            .getOverpayment()
                            .getSpecificationOfTheReasonsForOverpayments()
                            .getDocumentCarer()
                            .getId())
                    : documentsService.findById(fullOverpayment
                    .getOverpayment()
                    .getSpecificationOfTheReasonsForOverpayments()
                    .getDocumentPensioner()
                    .getId());
            Pensioner pensioner = pensionerService.findByIdRos(fullOverpayment.getId());

            InputStream inputStream = new ByteArrayInputStream(document.getDokument());
            XWPFDocument docxFile = new XWPFDocument(inputStream); // открываем файл и считываем его содержимое в объект XWPFDocument

            for (var table :
                    docxFile.getTables()) {
                for (var row :
                        table.getRows()) {
                    for (var cell :
                            row.getTableCells()) {
                        for (var paragraph :
                                cell.getParagraphs()) {
                            runs(
                                    paragraph.getRuns(),
                                    who.equals("carer") ? fullOverpayment.getOverpayment().getCarer() : pensioner,
                                    fullOverpayment
                            );
                        }
                    }
                }
            }

            var xwpfParagraphs = docxFile.getParagraphs();
            for (var paragraph :
                    xwpfParagraphs) {
                runs(
                        paragraph.getRuns(),
                        who.equals("carer") ? fullOverpayment.getOverpayment().getCarer() : pensioner,
                        fullOverpayment
                );
            }

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            docxFile.write(b);

            Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    toLatinTrans.transliterate(document.getNameFile()
                                            .replaceAll("ё", "е").replaceAll("Ё", "Е")
                                    ) + "\"")
                    .body(b.toByteArray());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private void runs(List<XWPFRun> xwpfRuns, Citizen citizen, FullOverpayment fullOverpayment) {
        for (int i = 0; i < xwpfRuns.size(); i++) {
            var run = xwpfRuns.get(i);

            StringBuilder text = new StringBuilder(run.text());
            while (text.toString().contains("$")) {
                if (text.toString().contains("$NAME_I")) {
                    run.setText(text.toString().replaceAll(
                            "\\$NAME_I",
                            toNormalCase(citizen.getName())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$NAME_R")) {
                    run.setText(text.toString().replaceAll(
                            "\\$NAME_R",
                            toNormalCase(citizen.getName(Case.Genitive))
                    ), 0);
                    break;
                }
                if (text.toString().contains("$FAM_I")) {
                    run.setText(text.toString().replaceAll(
                            "\\$FAM_I",
                            toNormalCase(citizen.getSurname())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$FAM_R")) {
                    run.setText(text.toString().replaceAll(
                            "\\$FAM_R",
                            toNormalCase(citizen.getSurname(Case.Genitive))
                    ), 0);
                    break;
                }
                if (text.toString().contains("$OT_I")) {
                    run.setText(text.toString().replaceAll(
                            "\\$OT_I",
                            toNormalCase(citizen.getSurname())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$OT_R")) {
                    run.setText(text.toString().replaceAll(
                            "\\$OT_R",
                            toNormalCase(citizen.getSurname(Case.Genitive))
                    ), 0);
                    break;
                }
                if (text.toString().contains("$INI")) {
                    run.setText(text.toString().replaceAll(
                            "\\$INI",
                            citizen.getInitials()
                    ), 0);
                    break;
                }
                if (text.toString().contains("$ADDR")) {
                    run.setText(text.toString().replaceAll(
                            "\\$ADDR",
                            citizen.getAdrreg()
                    ), 0);
                    break;
                }
                DateTimeFormatter formatterRu
                        = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                if (text.toString().contains("$PERIOD_S")) {
                    run.setText(text.toString().replaceAll(
                            "\\$PERIOD_S",
                            formatterRu.format(fullOverpayment.getSroks())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$PERIOD_PO")) {
                    run.setText(text.toString().replaceAll(
                            "\\$PERIOD_PO",
                            formatterRu.format(fullOverpayment.getSrokpo())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$SUMMA")) {
                    run.setText(text.toString().replaceAll(
                            "\\$SUMMA",
                            fullOverpayment.getSpe().toString()
                    ), 0);
                    break;
                }
                if (text.toString().contains("$DATEDOC")) {
                    run.setText(text.toString().replaceAll(
                            "\\$DATEDOC",
                            formatterRu.format(fullOverpayment.getDocdv())
                    ), 0);
                    break;
                }
                if (text.toString().contains("$NUMDOC")) {
                    run.setText(text.toString().replaceAll(
                            "\\$NUMDOC",
                            fullOverpayment.getDoc()
                    ), 0);
                    break;
                }
                text.append(xwpfRuns.get(++i).text());
                xwpfRuns.get(i).setText("", 0);
            }
            //System.out.println(run.text());
        }
    }

    private String toNormalCase(String str) {
        return str.substring(0, 1).toUpperCase() +
                str.substring(1).toLowerCase();
    }

}
