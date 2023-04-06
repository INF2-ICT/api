package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.service.TransactionService;
import com.quintor.api.util.JsonValidateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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

    @GetMapping("/get-all-transactions-json")
    public String getAllUsersJson() throws Exception {
//        @RequestHeader String apikey
        //Get all transactions from database
        ArrayList<TransactionModel> transactions = transactionService.getAllTransactions();

        Validatable validator = new JsonValidateUtil();
        JSONArray jsonList = new JSONArray();

        //Loop through all transaction models
        for (TransactionModel transaction : transactions) {
            JSONObject jsonTransaction = transactionService.convertTransactionToJson(transaction);
//
//          //Validate transaction through schema
            if (validator.validate("transaction.json", jsonTransaction.toString())) {
                jsonList.put(jsonTransaction);
            }
        }

        System.out.println(jsonList);
        String test = jsonList.toString();
        System.out.println(test);

        return jsonList.toString();
//        } else {
//            ArrayList<String> xmlList = new ArrayList<>(); //List of all transactions in xml objects
//            Validatable validator = new XmlValidateUtil();
//
//            //Loop through all transaction models
//            for (TransactionModel transaction : transactions) {
//                //Convert model to xml
//                String xmlTransaction = transactionService.convertTransactionToXml(transaction);
//
//                //Validate transaction through schema
//                if (validator.validate("transaction.xsd", xmlTransaction)) {
//                    xmlList.add(xmlTransaction);
//                }
//            }
//
//            //Return list with transactions
////            return xmlList;
//        }

//        return null;
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
