package com.quintor.api.controller;

import com.quintor.api.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

@RestController
public class ParserController {
    private ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/uploadMT940")
    public String uploadMT940(@RequestHeader String apikey, @RequestParam("file") MultipartFile file) throws Exception {
        //Check if api key is correct
        if (checkApiKey(apikey)) {
            parserService.parseMT940(file);
            return "success";
        } else {
            throw new Exception("Invalid API key");
        }
    }

    @PostMapping("/MT940toJSON")
    public String MT940toJSON(@RequestHeader String apikey, @RequestParam("file") MultipartFile file) throws Exception {
        //Check if api key is correct
        if (checkApiKey(apikey)) {
            return parserService.MT940toJSON(file);
        } else {
            throw new Exception("Invalid API key");
        }
    }
}
