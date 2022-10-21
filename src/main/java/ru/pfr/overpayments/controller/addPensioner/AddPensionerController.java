package ru.pfr.overpayments.controller.addPensioner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/overpayment"})
public class AddPensionerController {

    @GetMapping(value = { "/menuAddCitizen"})
    public String vievAddCitizen(){
        return "viev/addCitizen";
    }

}
