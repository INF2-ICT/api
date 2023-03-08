package com.quintor.api.service;

import com.google.gson.Gson;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import java.io.IOException;

@Service
public class ParserService {
    /**
     * Uploaded MT940 file
     */
    public void parseMT940(MultipartFile file) throws Exception {

    }

    /**
     * Must put MT940 to formatted JSON
     */
    public String MT940toJSON(MultipartFile file) throws IOException {
        //Convert file to String(txt)
        String content = new String(file.getBytes()); //Read MT940 file as String

        //Fields
        Gson gson = new Gson(); //Create new gson object "JSON"

        //Return JSON string
        return gson.toJson(content); //This now converts to RAW single line JSON
    }

    /**
     * Must put MT940 to formatted XML
     */
    private void MT940toXML(String content) {
        //Convert string to XML
    }

    /**
     * Function to validate MT940
     * @param file
     * @return
     */
    private boolean validateMT940 (MultipartFile file) throws IOException {
        MT940 mt940Parsed = MT940.parse(file.getInputStream()); //Readable for MT940 class

//    Must contain te following:
//    :20: transactie referentie - dagstatuut
//    :25: Iban eigenaar - dagstatuut
//    :60F: datum, balans voor aanpassing - dagstatuut
//    :62F: closing balance - dagstatuut

        //Get list of all field86's
//            List<Field86> test = mt940Parsed.getField86();
//            for (Field86 element : mt940Parsed.getField86()) {
//                System.out.println(element.getComponent1());
//            }

        //Print out other fields
//        System.out.println(mt940Parsed.getField60F());
//        System.out.println(mt940Parsed.getField62F());
//        System.out.println(mt940Parsed.getField28C());

        return false;
    }
}
