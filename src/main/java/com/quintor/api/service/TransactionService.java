package com.quintor.api.service;


import com.quintor.api.enums.TransactionType;
import com.quintor.api.model.TransactionModel;
import com.quintor.api.util.RelationalDatabaseUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
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
    public List<TransactionModel> getAllTransactionsJson() {
        List<TransactionModel> transactionsJson = new ArrayList<>();
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
                transactionsJson.add(new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionsJson;
    }

    public TransactionModel getTransactionJson(int transaction_id) {
        TransactionModel transactionJson = null;
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
                transactionJson = new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionJson;

    }

    /**
     * function to get all transactions from the mariadb
     *
     * @return list of all transactions
     */
    public List<Document> getAllTransactionsXml() {
        List<TransactionModel> transactionsXml = new ArrayList<>();
        ResultSet result;
        List<Document> koek = new ArrayList<>();

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
                transactionsXml.add(new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (TransactionModel allValues: transactionsXml) {
            String xml ="<?xml version='1.0' encoding='utf-8' ?>" +
                        "<root>" +
                        "<transactionType>" + allValues.getTransactionType() + "</transactionType>" +
                        "<additional_description>" + allValues.getAdditional_description() + "</additional_description>" +
                        "<beneficiary_reference>" + allValues.getBeneficiary_reference() + "</beneficiary_reference>" +
                        "<id>" + allValues.getId() + "</id>" +
                        "<transaction_reference>" + allValues.getTransaction_reference() + "</transaction_reference>" +
                        "<transactionCode>" + allValues.getTransactionCode() + "</transactionCode>" +
                        "<amount_in_euro>" + allValues.getAmount_in_euro() + "</amount_in_euro>" +
                        "<owner_reference>" + allValues.getOwner_reference() + "</owner_reference>" +
                        "<value_date>" + allValues.getValue_date() + "</value_date>" +
                        "<entry_date>" + allValues.getEntry_date() + "</entry_date>" +
                    "</root>";

            koek.add(convertStringToXml(xml));
        }
        System.err.println(koek);

        return koek;
    }

    public TransactionModel getTransactionXml(int transaction_id) {
        TransactionModel transactionXml = null;
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
                transactionXml = new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, amount_in_euro, transactionCode, owner_reference, beneficiary_reference, additional_description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionXml;

    }

    private Connection getConnection() {
        return connection;
    }

    // michel meuk
    private static Document convertStringToXml(String xmlString) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = dbf.newDocumentBuilder();

            return builder.parse(new InputSource(new StringReader(xmlString)));

        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

}
