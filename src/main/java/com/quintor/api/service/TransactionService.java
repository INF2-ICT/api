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
                String fund_code = result.getString(6); // result[6] = fund_code
                double amount_in_euro = result.getDouble(6); // result[6] = amount_in_euro
                String identifier_code = result.getString(7); // result[7] = identifier_code
                String owner_reference = result.getString(8); // result[8] = owner_reference
                String beneficiary_reference = result.getString(9); // result[9] = beneficiary_reference
                String supplementary_details = result.getString(10); // result[10] = extra details
                String line1 = result.getString(11);// result[11] = line
                String line2 = result.getString(12);// result[12] = line2
                String line3 = result.getString(13);// result[13] = line3
                String line4 = result.getString(14);// result[14] = line4
                String line5 = result.getString(15);// result[15] = line5
                String line6 = result.getString(16);// result[16] = line6
                String user_comment = result.getString(17);// result[17] = user comment

                /* add transaction to list of transactions */
                transactionsJson.add(new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, fund_code, amount_in_euro, identifier_code, owner_reference, beneficiary_reference, supplementary_details, line1, line2, line3, line4, line5, line6, user_comment));
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
                String fund_code = result.getString(6); // result[6] = fund_code
                double amount_in_euro = result.getDouble(7); // result[6] = amount_in_euro
                String identifier_code = result.getString(8); // result[7] = identifier_code
                String owner_reference = result.getString(9); // result[8] = owner_reference
                String beneficiary_reference = result.getString(10); // result[9] = beneficiary_reference
                String supplementary_details = result.getString(11); // result[10] = extra details
                String line1 = result.getString(12);// result[11] = line
                String line2 = result.getString(13);// result[12] = line2
                String line3 = result.getString(14);// result[13] = line3
                String line4 = result.getString(15);// result[14] = line4
                String line5 = result.getString(16);// result[15] = line5
                String line6 = result.getString(17);// result[16] = line6
                String user_comment = result.getString(18);// result[17] = user comment

                /* maken new transaction */
                transactionJson = new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, fund_code, amount_in_euro, identifier_code, owner_reference, beneficiary_reference, supplementary_details, line1, line2, line3, line4, line5, line6, user_comment);
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
        List<Document> array = new ArrayList<>();

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
                String fund_code = result.getString(6); // result[6] = fund_code
                double amount_in_euro = result.getDouble(6); // result[6] = amount_in_euro
                String identifier_code = result.getString(7); // result[7] = identifier_code
                String owner_reference = result.getString(8); // result[8] = owner_reference
                String beneficiary_reference = result.getString(9); // result[9] = beneficiary_reference
                String supplementary_details = result.getString(10); // result[10] = extra details
                String line1 = result.getString(11);// result[11] = line
                String line2 = result.getString(12);// result[12] = line2
                String line3 = result.getString(13);// result[13] = line3
                String line4 = result.getString(14);// result[14] = line4
                String line5 = result.getString(15);// result[15] = line5
                String line6 = result.getString(16);// result[16] = line6
                String user_comment = result.getString(17);// result[17] = user comment

                /* add transaction to list of transactions */
                transactionsXml.add(new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, fund_code, amount_in_euro, identifier_code, owner_reference, beneficiary_reference, supplementary_details, line1, line2, line3, line4, line5, line6, user_comment));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (TransactionModel allValues: transactionsXml) {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<root>" +
                    "<id>" + allValues.getId() + "</id>" +
                    "<transaction_reference>" + allValues.getTransaction_reference() +  "</transaction_reference>" +
                    "<value_date>" + allValues.getValue_date() + "</value_date>" +
                    "<entry_date>" + allValues.getEntry_date() + "</entry_date>" +
                    "<transactionType>" + allValues.getTransactionType() + "</transactionType>" +
                    "<fund_code>" + allValues.getFund_code() + "</fund_code>" +
                    "<amount_in_euro>" + allValues.getAmount_in_euro() + "</amount_in_euro>" +
                    "<identifier_code>" + allValues.getIdentifier_code() + "</identifier_code>" +
                    "<owner_reference>" + allValues.getOwner_reference() + "</owner_reference>" +
                    "<beneficiary_reference>" + allValues.getBeneficiary_reference() + "</beneficiary_reference>" +
                    "<supplementary_details>" + allValues.getSupplementary_details() + "</supplementary_details>" +
                    "<line1>" + allValues.getLine1() + "</line1>" +
                    "<line2>" + allValues.getLine2() + "</line2>" +
                    "<line3>" + allValues.getLine3() + "</line3>" +
                    "<line4>" + allValues.getLine4() + "</line4>" +
                    "<line5>" + allValues.getLine5() + "</line5>" +
                    "<line6>" + allValues.getLine6() + "</line6>" +
                    "<user_comment>" + allValues.getUser_comment() + "</user_comment>" +
                    "</root>";

            array.add(convertStringToXml(xml));
        }
        System.err.println(array);

        return array;
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
                String fund_code = result.getString(6); // result[6] = fund_code
                double amount_in_euro = result.getDouble(6); // result[6] = amount_in_euro
                String identifier_code = result.getString(7); // result[7] = identifier_code
                String owner_reference = result.getString(8); // result[8] = owner_reference
                String beneficiary_reference = result.getString(9); // result[9] = beneficiary_reference
                String supplementary_details = result.getString(10); // result[10] = extra details
                String line1 = result.getString(11);// result[11] = line
                String line2 = result.getString(12);// result[12] = line2
                String line3 = result.getString(13);// result[13] = line3
                String line4 = result.getString(14);// result[14] = line4
                String line5 = result.getString(15);// result[15] = line5
                String line6 = result.getString(16);// result[16] = line6
                String user_comment = result.getString(17);// result[17] = user comment

                /* maken new transaction */
                transactionXml = new TransactionModel(id, transaction_reference, value_date, entry_date, transactionType, fund_code, amount_in_euro, identifier_code, owner_reference, beneficiary_reference, supplementary_details, line1, line2, line3, line4, line5, line6, user_comment);
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
