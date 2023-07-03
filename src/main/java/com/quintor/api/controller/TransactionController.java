package com.quintor.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quintor.api.enums.TransactionType;
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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
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
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(transactionData);

                // Retrieving the values from the JSON object
                double amountInEuro = jsonNode.get("amountInEuro").asDouble();
                String transactionReference = jsonNode.get("transactionReference").asText();
                LocalDate date = LocalDate.parse(jsonNode.get("date").asText());
                String transactionType = jsonNode.get("transactionType").asText();
                String description = jsonNode.get("description").asText();

                return transactionService.createSingleTransaction(amountInEuro, transactionReference, date, TransactionType.valueOf(transactionType), description);
            }
        } else {
            Validatable validator = new XmlValidateUtil();

            System.out.println("xml");

            if (validator.validate("newTransaction.xsd", transactionData)) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(new InputSource(new StringReader(transactionData)));

                double amountInEuro = Double.parseDouble(document.getElementsByTagName("amount_in_euro").item(0).getTextContent());
                String transactionReference = document.getElementsByTagName("transaction_reference").item(0).getTextContent();
                String valueDate = document.getElementsByTagName("value_date").item(0).getTextContent();
                String transactionType = document.getElementsByTagName("transaction_type").item(0).getTextContent();
                String description = document.getElementsByTagName("description").item(0).getTextContent();

                return transactionService.createSingleTransaction(amountInEuro, transactionReference, LocalDate.parse(valueDate), TransactionType.valueOf(transactionType), description);
            }
        }
        return false;
    }
}
