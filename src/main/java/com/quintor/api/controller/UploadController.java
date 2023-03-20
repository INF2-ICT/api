package com.quintor.api.controller;

import com.quintor.api.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

@RestController
public class UploadController {
    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/post-xml")
    public void postXml(/**@RequestHeader String apikey,*/@RequestParam("xml") MultipartFile xml) {//throws Exception{
        //Check if api key is correct
//        if (!checkApiKey(apikey)) {
//            throw new Exception("Invalid API key");
//        }
        uploadService.uploadXML(xml);
    }

    @PostMapping("/post-json")
    public String postJson(@RequestParam("json") String json) throws Exception {
        //Check if api key is correct
//        if (!checkApiKey(apikey)) {
//            throw new Exception("Invalid API key");
//        }
//        uploadService.uploadXML(json);

        System.out.println(json);
        return "success";
    }
}
