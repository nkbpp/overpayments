package ru.pfr.overpayments.controller;

import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/index","/","","/overpayment/index"})
public class StartController {
    @GetMapping
    public String startIndex(
            //@AuthenticationPrincipal UserInfo user
    ){
        return "index";
    }

}
