package com.quintor.api.service;


import com.quintor.api.enums.TransactionType;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.util.RelationalDatabaseUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final Connection connection;

    public TransactionService() throws SQLException {
        this.connection = RelationalDatabaseUtil.getConnection();
    }

    /**
     * function to get all transactions from the mariadb
     * @return list of all transactions
     */
    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> transactions = new ArrayList<>();
        ResultSet result;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM Transaction")) {
            /* EXECUTE STATEMENT */
            result = stmt.executeQuery();

            /* Loop over results */
            while (result.next()) {
                /* Get all data from result */
                int id = result.getInt(1); // result[1] = id
                String transaction_reference = result.getString(2); // result[2] = transaction_reference
                LocalDate value_date = result.getDate(3).toLocalDate(); // result[3] = value_date
                LocalDate entry_date = result.getDate(4).toLocalDate(); // result[4] = entry_date
                TransactionType transactionType = TransactionType.valueOf(result.getString(5)); // result[5] = transaction type
                double amount_in_euro = result.getDouble(6); // result[6] = amount_in_euro
                String transactionCode = result.getString(7); // result[7] = transactionCode
                String owner_reference = result.getString(8); // result[8] = owner_reference
                String beneficiary_reference = result.getString(9); // result[9] = beneficiary_reference
                String additional_description = result.getString(10); // result[10] = optional additional description

                /* add transaction to list of transactions */
                transactions.add(new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public TransactionModel getTransaction(int transaction_id) {
        TransactionModel transaction = null;
        ResultSet result;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM Transaction WHERE ID = ?")) {
            /* Set transaction_id on ? */
            stmt.setInt(1, transaction_id);
            /* Execute statement */
            result = stmt.executeQuery();

            /* Loop over results */
            while (result.next()) {
                /* Get all data from result */
                int id = result.getInt(1); // result[1] = id
                String transaction_reference = result.getString(2); // result[2] = transaction_reference
                LocalDate value_date = result.getDate(3).toLocalDate(); // result[3] = value_date
                LocalDate entry_date = result.getDate(4).toLocalDate(); // result[4] = entry_date
                TransactionType transactionType = TransactionType.valueOf(result.getString(5)); // result[5] = transaction type
                double amount_in_euro = result.getDouble(6); // result[6] = amount_in_euro
                String transactionCode = result.getString(7); // result[7] = transactionCode
                String owner_reference = result.getString(8); // result[8] = owner_reference
                String beneficiary_reference = result.getString(9); // result[9] = beneficiary_reference
                String additional_description = result.getString(10); // result[10] = optional additional description

                /* maken new transaction */
                transaction = new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;

    }

    private Connection getConnection() {
        return connection;
    }

}
