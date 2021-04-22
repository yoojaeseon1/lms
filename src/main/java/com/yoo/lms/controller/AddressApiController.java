package com.yoo.lms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class AddressApiController {

    @GetMapping("/juso-popup")
    public String jusoPopup(){

        return "/jusoPopup";
    }

    @PostMapping("/juso-popup")
    public String jusoPopup(HttpServletRequest request, Model model){

        String roadFullAddr = request.getParameter("roadFullAddr");
        String inputYn = request.getParameter("inputYn");
        String zipNo = request.getParameter("zipNo");
        String roadAddrPart1 = request.getParameter("roadAddrPart1");
        String addrDetail = request.getParameter("addrDetail");
        String roadAddrPart2 = request.getParameter("roadAddrPart2");

        model.addAttribute("roadFullAddr", roadFullAddr);
        model.addAttribute("inputYn", inputYn);
        model.addAttribute("zipNo",  zipNo);
        model.addAttribute("addrDetail", addrDetail);
        model.addAttribute("roadAddrPart1", roadAddrPart1);
        model.addAttribute("roadAddrPart2", roadAddrPart2);

        return "/jusoPopup";
    }
}
