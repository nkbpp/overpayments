package ru.pfr.overpayments.controller.overpayment.referenceBook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/overpayment/referenceBook"})
public class DepartmentController {

    @GetMapping(value = {"/vievDepartment"})
    public String vievReferenceBookReasonsForOverpayments(
    ) {
        return "viev/referenceBook/vievDepartment";
    }

}
