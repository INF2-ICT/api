package com.quintor.api.controller;

import com.quintor.api.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

@RestController
public class ParserController {
    private ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/uploadMT940")
    public String uploadFile(@RequestHeader String apikey, @RequestParam("file") MultipartFile file) throws Exception {
        //Check if api key is correct
        if (checkApiKey(apikey)) {
            parserService.parseMT940();
            return "success";
        } else {
            throw new Exception("Invalid API key");
        }
    }
}
