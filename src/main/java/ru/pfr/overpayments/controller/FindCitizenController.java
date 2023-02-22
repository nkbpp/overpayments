package ru.pfr.overpayments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.overpayments.model.dto.FIODto;
import ru.pfr.overpayments.model.dto.SNILSDto;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.service.overpayment.DistrictService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/overpayment"})
@PreAuthorize("hasRole('USER')")
//@PreAuthorize("hasRole('OZIADMIN')")
public class FindCitizenController {

    private final DistrictService districtService;

    private final DistrictMapper districtMapper;

    @ModelAttribute(name = "district")
    public List<DistrictDto> district() {
        return districtService.findAll()
                .stream()
                .map(districtMapper::toDto)
                .toList();
    }

    @GetMapping(value = {"/vievFindCitizenBySnils"})
    public String vievFindCitizenBySnils(
            Model model
    ) {
        model.addAttribute("find", "snils");
        model.addAttribute("snilsdto", new SNILSDto());
        return "viev/findCitizen";
    }

    @GetMapping(value = { "/vievFindCitizenByFio"})
    public String vievFindCitizenByFio(
            Model model
    ){
        model.addAttribute("find", "fio");
        model.addAttribute("fiodto", new FIODto());
        return "viev/findCitizen";
    }

    @GetMapping(value = { "/vievFindCitizenByDistrict"})
    public String vievFindCitizenByDistrict(
            Model model
    ){
        model.addAttribute("find", "district");
        return "viev/findCitizen";
    }

}
