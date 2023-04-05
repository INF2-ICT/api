package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.service.TransactionService;
import com.quintor.api.util.JsonValidateUtil;
import com.quintor.api.util.XmlValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/get-all-transactions")
    public ArrayList<String> getAllUsers(String mode) throws Exception {
//        @RequestHeader String apikey
        //Get all transactions from database
        ArrayList<TransactionModel> transactions = transactionService.getAllTransactions();

        //Check if mode was json/xml
        if (mode.equalsIgnoreCase("json")) {
            Validatable validator = new JsonValidateUtil();
            ArrayList<String> jsonList = new ArrayList<>(); //List of all transactions in json objects

            //Loop through all transaction models
            for (TransactionModel transaction : transactions) {
                //Convert model to json
                String jsonTransaction = transactionService.convertTransactionToJson(transaction);

                //Validate transaction through schema
                if (validator.validate("transaction.json", jsonTransaction)) {
                    jsonList.add(jsonTransaction);
                }
            }

            //Return list with transactions
            return jsonList;
        } else {
            ArrayList<String> xmlList = new ArrayList<>(); //List of all transactions in xml objects
            Validatable validator = new XmlValidateUtil();

            //Loop through all transaction models
            for (TransactionModel transaction : transactions) {
                //Convert model to xml
                String xmlTransaction = transactionService.convertTransactionToXml(transaction);

                //Validate transaction through schema
                if (validator.validate("transaction.xsd", xmlTransaction)) {
                    xmlList.add(xmlTransaction);
                }
            }

            //Return list with transactions
            return xmlList;
        }
    }

    @GetMapping("/get-transaction")
    public String getUser(@RequestParam int id, String mode) throws Exception {
//        @RequestHeader String apikey,
        if (mode.toLowerCase().equals("json")) {

        } else {

        }

        return null;
    }
}
