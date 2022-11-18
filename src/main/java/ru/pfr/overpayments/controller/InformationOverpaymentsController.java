package ru.pfr.overpayments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.DepartmentMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/overpayment/vievInformationOverpayments")
public class InformationOverpaymentsController {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;
    private final SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;
    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    /**
     * Найти ID
     */
    @GetMapping(path = "/{id}")
    public String findById(@PathVariable("id") String id,
                           Model model) {

        var reasonsForOverpayments = reasonsForOverpaymentsService.findAll().stream().map(
                reasonsForOverpaymentsMapper::toDto
        ).collect(Collectors.toList());
        model.addAttribute("selectReasonsForOverpayments", reasonsForOverpayments);
        model.addAttribute("department", departmentService.findAll().stream().map(
                departmentMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("id", id);
        model.addAttribute("idRos", "");
        return "viev/overpaymentData";
    }

    @GetMapping(path = "/IdRos/{id}")
    public String findByIdRos(@PathVariable("id") String idRos,
                              Model model) {
        model.addAttribute("selectReasonsForOverpayments", reasonsForOverpaymentsService.findAll().stream().map(
                reasonsForOverpaymentsMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("se  lectSpecificationOfTheReasonsForOverpayments", specificationOfTheReasonsForOverpaymentsService.findAll().stream().map(
                specificationOfTheReasonsForOverpaymentsMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("department", departmentService.findAll().stream().map(
                departmentMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("id", "");
        model.addAttribute("idRos", idRos);
        return "viev/overpaymentData";
    }

}
