package com.quintor.api.service;

import com.quintor.api.enums.TransactionType;
import com.quintor.api.model.TransactionModel;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.quintor.api.util.RelationalDatabaseUtil.getConnection;

@Service
public class TransactionService {
    public ArrayList<TransactionModel> getAllTransactions() {
        ArrayList<TransactionModel> listOfTransactions = new ArrayList<>();
        ResultSet result;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM get_all_transactions")) {
            //Execute statement
            result = stmt.executeQuery();

            //Loop over results
            while (result.next()) {
                //Get all data from result
                int id = result.getInt(1);
                String transaction_reference = result.getString(2);
                LocalDate value_date = result.getDate(3).toLocalDate();
                TransactionType transactionType = TransactionType.valueOf(result.getString(4));
                double amount_in_euro = result.getDouble(5);

                //Create new object
                TransactionModel transaction = new TransactionModel(id, transaction_reference, value_date, transactionType, amount_in_euro);
                //Add object to list of objects
                listOfTransactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfTransactions;
    }

    public JSONObject convertTransactionToJson (TransactionModel transaction) {
        return new JSONObject()
                .put("id", transaction.getId())
                .put("transaction_reference", transaction.getTransaction_reference())
                .put("value_date", transaction.getValue_date().toString())
                .put("transaction_type", transaction.getTransactionType().toString())
                .put("amount_in_euro", transaction.getAmount_in_euro());
    }

    public String convertTransactionToXml (TransactionModel transaction) {
        return
                "<transaction>" +
                    "<id>" + transaction.getId() + "</id>" +
                    "<transaction_reference>" + transaction.getTransaction_reference() + "</transaction_reference>" +
                    "<value_date>" + transaction.getValue_date().toString() + "</value_date>" +
                    "<transaction_type>" + transaction.getTransactionType().toString() + "</transaction_type>" +
                    "<amount_in_euro>" + transaction.getAmount_in_euro() + "</amount_in_euro>" +
                "</transaction>";
    }
}
