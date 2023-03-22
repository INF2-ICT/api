package com.quintor.api.controller;

import com.quintor.api.model.TransactionModel;
import com.quintor.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

// Define a controller class for handling transactions
@RestController
public class TransactionController {

    // Inject the transaction service into the controller using the @Autowired annotation
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Define a REST endpoint for retrieving all transactions
    @GetMapping("/get-all-transactions")
    public List getAllTransactions(@RequestHeader String apikey) throws Exception {

        // Check if the API key is valid
        if (checkApiKey(apikey)) {
            // Call the transaction service to retrieve all transactions
            return transactionService.getAllTransactions();
        } else {
            // Throw an exception if the API key is invalid
            throw new Exception("Invalid API key");
        }
    }

    // Define a REST endpoint for retrieving a specific transaction
    @GetMapping("/get-transaction")
    public TransactionModel getTransaction(@RequestHeader String apikey, @RequestParam int id) throws Exception {

        // Check if the API key is valid
        if (checkApiKey(apikey)) {
            // Call the transaction service to retrieve the specified transaction
            return transactionService.getTransaction(id);
        } else {
            // Throw an exception if the API key is invalid
            throw new Exception("Invalid API key");
        }
    }

}
