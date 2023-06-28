package com.quintor.api.service;

import com.quintor.api.enums.TransactionType;
import com.quintor.api.model.SingleTransactionModel;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.util.RelationalDatabaseUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.*;
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

    public SingleTransactionModel getSingleTransaction(int transactionId) throws SQLException {
        Connection sqlConnection = RelationalDatabaseUtil.getConnection();

        String query = "{ CALL get_single_transaction(?) }";

        CallableStatement statement = sqlConnection.prepareCall(query);

        statement.setInt(1, transactionId);

        ResultSet result = statement.executeQuery();

        SingleTransactionModel transactionModel = null;

        // Process the result set
        if (result.next()) {
            // Get the data from the result set
            int id = result.getInt(1);
            String transactionReference = result.getString(2);
            String description = result.getString(3);
            LocalDate value_date = result.getDate(4).toLocalDate();
            TransactionType transactionType = TransactionType.valueOf(result.getString(5));
            double amountInEuro = result.getDouble(6);
            String identificationCode = result.getString(7);
            String ownerReferential = result.getString(8);

            // Create a new SingleTransactionModel object
            transactionModel = new SingleTransactionModel(id, transactionReference, amountInEuro, description, value_date, transactionType, identificationCode, ownerReferential);
        }

        // Return the SingleTransactionModel object
        return transactionModel;
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

    public JSONObject convertSingleTransactionToJson (SingleTransactionModel transaction) {
        return new JSONObject()
                .put("id", transaction.getId())
                .put("transaction_reference", transaction.getTransactionReference())
                .put("description", transaction.getDescription())
                .put("value_date", transaction.getValue_date().toString())
                .put("transaction_type", transaction.getTransactionType().toString())
                .put("amount_in_euro", transaction.getAmountInEuro())
                .put("identification_code", transaction.getIdentificationCode())
                .put("owner_referential", transaction.getOwnerReferential());
    }

    public String convertSingleTransactionToXml (SingleTransactionModel transaction) {
        return
                "<transaction>" +
                        "<id>" + transaction.getId() + "</id>" +
                        "<transaction_reference>" + transaction.getTransactionReference() + "</transaction_reference>" +
                        "<description>" + transaction.getDescription() + "</description>" +
                        "<value_date>" + transaction.getValue_date() + "</value_date>" +
                        "<transaction_type>" + transaction.getTransactionType() + "</transaction_type>" +
                        "<amount_in_euro>" + transaction.getAmountInEuro() + "</amount_in_euro>" +
                        "<identification_code>" + transaction.getIdentificationCode() + "</identification_code>" +
                        "<owner_referential>" + transaction.getOwnerReferential() + "</owner_referential>" +
                "</transaction>";
    }
}
