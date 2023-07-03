package com.quintor.api.controller;

import com.quintor.api.interfaces.Validatable;
import com.quintor.api.model.SingleTransactionModel;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.service.TransactionService;
import com.quintor.api.util.JsonValidateUtil;
import com.quintor.api.util.XmlValidateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/transaction/{mode}/{id}")
    public String getSingleJsonTransaction(@PathVariable String mode, @PathVariable int id) throws Exception {
        SingleTransactionModel transaction = transactionService.getSingleTransaction(id);
        if (transaction.getDescription() == null || transaction.getDescription().equals("")) {
            transaction.setDescription("Geen beschrijving");
        }

        if (mode.toLowerCase().equals("json")) {
            JSONArray jsonList = new JSONArray();
            JSONObject jsonTransaction = transactionService.convertSingleTransactionToJson(transaction);
            jsonList.put(jsonTransaction);
            return jsonList.toString();
        } else if (mode.equals("xml")){
            return transactionService.convertSingleTransactionToXml(transaction);
        }

        return null;
    }

    @PostMapping("/transaction/{mode}")
    public boolean createTransaction(@PathVariable String mode, @RequestParam("transactionData") String transactionData) throws Exception {
        System.out.println(transactionData);
        System.out.println(mode);

        if (mode.toLowerCase().equals("json")) {
            Validatable validator = new JsonValidateUtil();

            System.out.println("json");

            if (validator.validate("newTransaction.json", transactionData)) {
                return true;
            }
        } else {
            Validatable validator = new XmlValidateUtil();

            System.out.println("xml");

            if (validator.validate("newTransaction.xsd", transactionData)) {
                return true;
            }


        }
        return false;
    }
}
