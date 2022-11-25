package ru.pfr.overpayments.controller.overpayment.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/admin"})
@PreAuthorize("hasRole('OZIADMIN')")
public class LogiController {

    @GetMapping(value = {""})
    public String vievReferenceBookReasonsForOverpayments(
    ) {
        return "viev/admin/vievAdmin";
    }

}
