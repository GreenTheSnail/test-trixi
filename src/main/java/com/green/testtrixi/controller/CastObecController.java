package com.green.testtrixi.controller;

import com.green.testtrixi.serviceImpl.CastObceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CastObecController {

    private final CastObceServiceImpl castObceService;

    @Autowired
    private CastObecController(CastObceServiceImpl castObceService) {
        this.castObceService = castObceService;
    }

    @GetMapping("/{obecId}/parts")
    public String parts(@PathVariable Long obecId, Model model) {
        model.addAttribute("parts", castObceService.getAllByObecId(obecId));
        return "parts";
    }
}
