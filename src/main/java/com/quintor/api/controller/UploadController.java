package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.service.UploadService;
import com.quintor.api.util.JsonValidateUtil;
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
        System.out.println(json);
        //Check if api key is correct
//        if (!checkApiKey(apikey)) {
//            throw new Exception("Invalid API key");
//        }

        //Validate with schema
        Validatable validator = new JsonValidateUtil();
        if (validator.validate("mt940.json", json)) {
            System.out.println("Helemaaaal goud!");
        } else {
            System.out.println("Neit goud");
        }

        //Upload to database
        uploadService.uploadJSON(json);

        //Return something back to application that all went right
        return "success";
    }
}
