package ru.pfr.overpayments.controller;

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
