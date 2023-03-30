package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.service.UploadService;
import com.quintor.api.util.JsonValidateUtil;
import com.quintor.api.util.XmlValidateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {
    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/post-xml")
    public String postXml(@RequestParam("xml") String xml) throws Exception {//throws Exception{
        //Check if api key is correct
//        if (!checkApiKey(apikey)) {
//            throw new Exception("Invalid API key");
//        }

        Validatable validator = new XmlValidateUtil();
        if (validator.validate("mt940.xsd", xml)) {
            uploadService.uploadXML(xml); //Upload to database
            return "Success";
        } else {
            return "XML is not valid";
        }
    }

    @PostMapping("/post-json")
    public String postJson(@RequestParam("json") String json) throws Exception {
        /**@RequestHeader String apikey,*/
        //Check if api key is correct
//        if (!checkApiKey(apikey)) {
//            throw new Exception("Invalid API key");
//        }

        //Validate with schema
        Validatable validator = new JsonValidateUtil();
        if (validator.validate("mt940.json", json)) {
            uploadService.uploadJSON(json); //Upload to database
            return "Success";
        } else {
            return "JSON is not valid";
        }
    }

    @PostMapping("/post-raw")
    public String insertMt940()///**@RequestHeader String apikey,*/@RequestParam("MT940File") File file) throws IOException
    {
        uploadService.uploadRaw();
        return "success";
    }
}
