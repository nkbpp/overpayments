package ru.pfr.overpayments.controller.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.overpayments.model.overpayment.mapper.DocumentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.DocumentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/overpayment/referenceBook"})
public class SpecificationOfTheReasonsForOverpaymentsController {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;
    private final DocumentsService documentsService;
    private final DocumentsMapper documentsMapper;

    @GetMapping(value = { "/vievReferenceBookSpecificationOfTheReasonsForOverpayments"})
    public String vievReferenceBookReasonsForOverpayments(Model model){

        model.addAttribute("selectReasonsForOverpayments", reasonsForOverpaymentsService.findAll()
                .stream()
                .map(reasonsForOverpaymentsMapper::toDto)
                .collect(Collectors.toList())
        );

        model.addAttribute("selectDocuments", documentsService.findAll()
                .stream()
                .map(documentsMapper::toDto)
                .collect(Collectors.toList())
        );

        return "viev/referenceBook/vievReferenceBookSpecificationOfTheReasonsForOverpayments";
    }
}
