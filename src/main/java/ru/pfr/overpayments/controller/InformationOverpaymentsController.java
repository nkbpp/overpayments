package ru.pfr.overpayments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.DepartmentDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.DepartmentMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.util.List;

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

    @ModelAttribute(name = "selectReasonsForOverpayments")
    public List<ReasonsForOverpaymentsDto> selectReasonsForOverpayments() {
        return reasonsForOverpaymentsService.findAll()
                .stream()
                .map(reasonsForOverpaymentsMapper::toDto)
                .toList();
    }

    @ModelAttribute(name = "department")
    public List<DepartmentDto> department() {
        return departmentService.findAll()
                .stream()
                .map(departmentMapper::toDto)
                .toList();
    }

    @ModelAttribute(name = "selectSpecificationOfTheReasonsForOverpayments")
    public List<SpecificationOfTheReasonsForOverpaymentsDto> selectSpecificationOfTheReasonsForOverpayments() {
        return specificationOfTheReasonsForOverpaymentsService.findAll()
                .stream()
                .map(specificationOfTheReasonsForOverpaymentsMapper::toDto)
                .toList();
    }


    /**
     * Найти ID
     */
    @GetMapping(path = "/{id}")
    public String findById(
            @PathVariable("id") String id,
            Model model
    ) {
        model.addAttribute("id", id);
        model.addAttribute("idRos", "");
        return "viev/overpaymentData";
    }

    @GetMapping(path = "/IdRos/{id}")
    public String findByIdRos(
            @PathVariable("id") String idRos,
            Model model
    ) {
        model.addAttribute("id", "");
        model.addAttribute("idRos", idRos);
        return "viev/overpaymentData";
    }

}
