package com.clientui.mediscreen.controller;


import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.repository.MsPatientProxy;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Controller
@Api("Home page")
public class HomeController {



    public HomeController() {

    }


    @GetMapping("/")
    public String home(){
        return "home";
    }




}
