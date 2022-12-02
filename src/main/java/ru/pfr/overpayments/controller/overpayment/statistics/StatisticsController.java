package ru.pfr.overpayments.controller.overpayment.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.model.overpayment.mapper.DocumentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.DepartmentMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.DistrictService;
import ru.pfr.overpayments.service.overpayment.DocumentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/overpayment/statistics"})
public class StatisticsController {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;
    private final SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;
    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;
    private final DistrictService districtService;
    private final DistrictMapper districtMapper;

    @GetMapping(value = {""})
    public String getViev(
            Model model
    ) {
        model.addAttribute("selectReasonsForOverpayments", reasonsForOverpaymentsService.findAll().stream().map(
                reasonsForOverpaymentsMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("selectSpecificationOfTheReasonsForOverpayments", specificationOfTheReasonsForOverpaymentsService.findAll().stream().map(
                specificationOfTheReasonsForOverpaymentsMapper::toDto
        ).collect(Collectors.toList()));
        model.addAttribute("district", districtService.findAll().stream().map(
                districtMapper::toDto
        ).collect(Collectors.toList()));

        return "viev/vievStatistics";
    }
}
