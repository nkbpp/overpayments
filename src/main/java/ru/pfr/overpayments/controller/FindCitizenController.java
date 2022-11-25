package ru.pfr.overpayments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.overpayments.model.dto.FIODto;
import ru.pfr.overpayments.model.dto.SNILSDto;
import ru.pfr.overpayments.model.overpayment.mapper.DistrictMapper;
import ru.pfr.overpayments.service.overpayment.DistrictService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/overpayment"})
@PreAuthorize("hasRole('USER')")
//@PreAuthorize("hasRole('OZIADMIN')")
public class FindCitizenController {

    private final DistrictService districtService;

    private final DistrictMapper districtMapper;

    @GetMapping(value = { "/vievFindCitizenBySnils"})
    public String vievFindCitizenBySnils(
            Model model
    ){
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
        model.addAttribute("district", districtService.findAll().stream().map(
                districtMapper::toDto
        ).collect(Collectors.toList()));
        return "viev/findCitizen";
    }

}
