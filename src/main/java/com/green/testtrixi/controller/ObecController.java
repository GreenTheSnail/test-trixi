package com.green.testtrixi.controller;

import com.green.testtrixi.serviceImpl.FileServiceImpl;
import com.green.testtrixi.serviceImpl.ObecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class ObecController {

    private final ObecServiceImpl obecService;

    private final FileServiceImpl fileService;

    @Autowired
    private ObecController(ObecServiceImpl obecService, FileServiceImpl fileService) {
        this.obecService = obecService;
        this.fileService = fileService;
    }

    @GetMapping("/show")
    public String all(Model model) {
        model.addAttribute("results", obecService.findAll());
        return "results";
    }

    @GetMapping("/start")
    public String results(@RequestParam(required = false) String url) {
        if(url == null || url.equals("") || url.isEmpty()){
            url = "https://www.smartform.cz/download/20210331_OB_573060_UZSZ.xml.zip";
        }
        String fileLoc = System.getProperty("user.dir");
        String fileName = "/newFile.zip";

        fileService.download(url, fileLoc + fileName);
        String filePath = fileService.unzip(fileLoc + fileName, fileLoc);
        fileService.processXMLFile(filePath);
        return "redirect:/show";
    }
}
