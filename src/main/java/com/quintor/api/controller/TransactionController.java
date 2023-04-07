package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.service.TransactionService;
import com.quintor.api.util.JsonValidateUtil;
import com.quintor.api.util.XmlValidateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/get-all-transactions-json")
    public String getAllTransactionJson(/* @RequestHeader String apikey */) throws Exception {
//        Check if API auth is correct
//        if (checkApiKey(apikey)) {
//            return userService.getAllUsers(); // [ {}, {} ]
//        } else {
//            throw new Exception("Invalid API key");
//        }
        //Get all transactions from database
        ArrayList<TransactionModel> transactions = transactionService.getAllTransactions();
        Validatable validator = new JsonValidateUtil();
        JSONArray jsonList = new JSONArray();

        //Loop through all transaction models
        for (TransactionModel transaction : transactions) {
            JSONObject jsonTransaction = transactionService.convertTransactionToJson(transaction);

            //Validate transaction through schema
            if (validator.validate("transaction.json", jsonTransaction.toString())) {
                jsonList.put(jsonTransaction);
            }
        }

        //Return list with all transactions in json
        return jsonList.toString();
    }

    @GetMapping("/get-all-transactions-xml")
    public ArrayList<String> getAllTransactionXml(/* @RequestHeader String apikey */) throws Exception {
//        Check if API auth is correct
//        if (checkApiKey(apikey)) {
//            return userService.getAllUsers(); // [ {}, {} ]
//        } else {
//            throw new Exception("Invalid API key");
//        }

        //Get all transactions from database
        ArrayList<TransactionModel> transactions = transactionService.getAllTransactions();
        Validatable validator = new XmlValidateUtil();
        ArrayList<String> xmlList = new ArrayList<>();

        //Loop through all transaction models
        for (TransactionModel transaction : transactions) {
            //Convert model to xml
            String xmlTransaction = transactionService.convertTransactionToXml(transaction);

            //Validate transaction through schema
            if (validator.validate("transaction.xsd", xmlTransaction)) {
                xmlList.add(xmlTransaction);
            }
        }

        //Return list with all transactions in xml
        return xmlList;
    }

    @GetMapping("/get-transaction")
    public String getTransactionById(/* @RequestHeader String apikey, */ @RequestParam int id, String mode) throws Exception {
//        Check if API auth is correct
//        if (checkApiKey(apikey)) {
//            return userService.getAllUsers(); // [ {}, {} ]
//        } else {
//            throw new Exception("Invalid API key");
//        }
        return null;
    }
}
