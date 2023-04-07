package com.quintor.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.client.MongoCollection;

import com.mongodb.client.result.InsertOneResult;
import com.quintor.api.util.NoSqlDatabaseUtil;
import com.quintor.api.util.RelationalDatabaseUtil;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;


@Service
public class UploadService {
    private final Connection sqlConnection;

    private final MongoCollection mongoConnection;

    public UploadService() throws SQLException {
        this.sqlConnection = RelationalDatabaseUtil.getConnection();
        this.mongoConnection = NoSqlDatabaseUtil.getConnection();
    }

    public void uploadXML (String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
            String reference = document.getElementsByTagName("reference").item(0).getTextContent();
            String account = document.getElementsByTagName("account").item(0).getTextContent();
            String statementNR = document.getElementsByTagName("statementNumber").item(0).getTextContent();
            String sequenceNR = document.getElementsByTagName("sequenceNumber").item(0).getTextContent();
            String creditDebit = document.getElementsByTagName("creditDebit").item(0).getTextContent();
            int day = Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(4,6));
            int month = Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(2,4))-1;
            int year = 100 + Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(0,2));
            Date date = new Date(year, month, day);
            String currency = document.getElementsByTagName("currency").item(0).getTextContent();
            double startingBalance = Double.parseDouble(document.getElementsByTagName("amount").item(0).getTextContent().replace(",","."));
            int endOfNode = document.getElementsByTagName("amount").getLength()-1;
            double closingBalance = Double.parseDouble(document.getElementsByTagName("amount").item(endOfNode).getTextContent().replace(",","."));
            uploadMT940(reference, account, statementNR, sequenceNR, creditDebit, date, currency, startingBalance, closingBalance);
            for (int i = 0; i < document.getElementsByTagName("amount").getLength()-2; i++) {
                int valueDay = Integer.parseInt(document.getElementsByTagName("valueDate").item(i).getTextContent().substring(4, 6));
                int valueMonth = Integer.parseInt(document.getElementsByTagName("valueDate").item(i).getTextContent().substring(2,4))-1;
                int valueYear = 100 + Integer.parseInt(document.getElementsByTagName("valueDate").item(i).getTextContent().substring(0, 2));
                Date valueDate = new Date(valueYear, valueMonth, valueDay);
                int entryDay = Integer.parseInt(document.getElementsByTagName("entryDate").item(i).getTextContent().substring(2,4));
                int entryMonth = Integer.parseInt(document.getElementsByTagName("entryDate").item(i).getTextContent().substring(0, 2))-1;
                Date entryDate = new Date(valueYear, entryMonth, entryDay);
                String type = document.getElementsByTagName("creditDebit").item(i).getTextContent();
                String fundCode = document.getElementsByTagName("fundCode").item(i).getTextContent();
                Double amount = Double.parseDouble(document.getElementsByTagName("amount").item(i+1).getTextContent().replace(",","."));
                String identifierCode = document.getElementsByTagName("identifierCode").item(i).getTextContent();
                String customerReference = document.getElementsByTagName("customerReference").item(i).getTextContent();
                String bankReference = document.getElementsByTagName("bankReference").item(i).getTextContent();
                String supplementaryDetails = document.getElementsByTagName("supplementaryDetails").item(i).getTextContent();
                String line1 = document.getElementsByTagName("line1").item(i).getTextContent();
                String line2 = document.getElementsByTagName("line2").item(i).getTextContent();
                String line3 = document.getElementsByTagName("line3").item(i).getTextContent();
                String line4 = document.getElementsByTagName("line4").item(i).getTextContent();
                String line5 = document.getElementsByTagName("line5").item(i).getTextContent();
                String line6 = document.getElementsByTagName("line6").item(i).getTextContent();
                uploadTransacion(reference, valueDate, entryDate, type, fundCode, amount, identifierCode, customerReference, bankReference, supplementaryDetails, line1, line2, line3, line4, line5, line6);
            }

        } catch (IOException | ParserConfigurationException | SAXException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void uploadJSON(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            String reference = rootNode.get("MT940").get("line20").get("reference").asText();
            String account = rootNode.get("MT940").get("line25").get("account").asText();
            String statementNR = rootNode.get("MT940").get("line28C").get("statementNumber").asText();
            String sequenceNR = rootNode.get("MT940").get("line28C").get("sequenceNumber").asText();
            String creditDebit = rootNode.get("MT940").get("line60F").get("creditDebit").asText();
            int day = Integer.parseInt(rootNode.get("MT940").get("line60F").get("date").asText().substring(4,6));
            int month = Integer.parseInt(rootNode.get("MT940").get("line60F").get("date").asText().substring(2,4))-1;
            int year = 100 + Integer.parseInt(rootNode.get("MT940").get("line60F").get("date").asText().substring(0,2));
            Date date = new Date(year, month, day);
            String curency = rootNode.get("MT940").get("line60F").get("currency").asText();
            double startingBalance = Double.parseDouble(rootNode.get("MT940").get("line60F").get("amount").asText().replace(",","."));
            double closingBalance = Double.parseDouble(rootNode.get("MT940").get("line62F").get("amount").asText().replace(",","."));
            uploadMT940(reference, account, statementNR, sequenceNR, creditDebit, date, curency, startingBalance, closingBalance);
            for (int i = 0; i < rootNode.get("MT940").get("statements").size(); i++) {
                int valueDay = Integer.parseInt(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("valueDate").asText().substring(4, 6));
                int valueMonth = Integer.parseInt(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("valueDate").asText().substring(2,4))-1;
                int valueYear = 100 + Integer.parseInt(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("valueDate").asText().substring(0, 2));
                Date valueDate = new Date(valueYear, valueMonth, valueDay);
                int entryDay = Integer.parseInt(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("entryDate").asText().substring(2,4));
                int entryMonth = Integer.parseInt(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("entryDate").asText().substring(0, 2))-1;
                Date entryDate = new Date(valueYear, entryMonth, entryDay);
                String type = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("creditDebit").asText();
                String fundCode = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("fundCode").asText();
                Double amount = Double.parseDouble(rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("amount").asText().replace(",","."));
                String identifierCode = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("identifierCode").asText();
                String customerReference = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("customerReference").asText();
                String bankReference = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("bankReference").asText();
                String supplementaryDetails = rootNode.get("MT940").get("statements").get("statement"+i).get("line61").get("supplementaryDetails").asText();
                String line1 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line1").asText();
                String line2 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line2").asText();
                String line3 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line3").asText();
                String line4 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line4").asText();
                String line5 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line5").asText();
                String line6 = rootNode.get("MT940").get("statements").get("statement"+i).get("line86").get("line6").asText();
                uploadTransacion(reference, valueDate, entryDate, type, fundCode, amount, identifierCode, customerReference, bankReference, supplementaryDetails, line1, line2, line3, line4, line5, line6);
            }

        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void uploadMT940(String reference,
                            String account,
                            String statementNR,
                            String sequenceNR,
                            String creditDebit,
                            Date date,
                            String currency,
                            double startingBalance,
                            double closingBalance)
            throws SQLException {

        String query = "{ CALL add_MT940(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        CallableStatement statement = sqlConnection.prepareCall(query);

        statement.setString(1, reference);
        statement.setString(2, account);
        statement.setString(3, statementNR);
        statement.setString(4, sequenceNR);
        statement.setString(5, creditDebit);
        statement.setDate(6, date);
        statement.setString(7, currency);
        statement.setDouble(8, startingBalance);
        statement.setDouble(9, closingBalance);

        statement.executeQuery();
    }

    public void uploadTransacion(String reference,
                                 Date valueDate,
                                 Date entryDate,
                                 String type,
                                 String fundCode,
                                 Double amount,
                                 String idCode,
                                 String customerReference,
                                 String bankReference,
                                 String supplementaryDetails,
                                 String line1,
                                 String line2,
                                 String line3,
                                 String line4,
                                 String line5,
                                 String line6) throws SQLException {

        String query = "{ CALL add_transaction(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        CallableStatement statement = sqlConnection.prepareCall(query);

        statement.setString(1, reference);
        statement.setDate(2, valueDate);
        statement.setDate(3, entryDate);
        statement.setString(4, type);
        statement.setString(5, fundCode);
        statement.setDouble(6, amount);
        statement.setString(7, idCode);
        statement.setString(8, customerReference);
        statement.setString(9, bankReference);
        statement.setString(10, supplementaryDetails);
        statement.setString(11, line1);
        statement.setString(12, line2);
        statement.setString(13, line3);
        statement.setString(14, line4);
        statement.setString(15, line5);
        statement.setString(16, line6);

        statement.executeQuery();
    }

    public boolean uploadRaw(String mt940)
    {
        org.bson.Document document = new org.bson.Document();
        document.append("_id", new ObjectId());
        document.append("MT940", mt940);
        if (mongoConnection.insertOne(document).equals(InsertOneResult.class))
        {
            return true;
        }
        return false;
    }

    public boolean addCash(double amount, String description)
    {
        java.util.Date date = new java.util.Date();
        long dateLong = date.getTime();
        Timestamp sqlDate = new Timestamp(dateLong);

        String query = "{ CALL add_cash(?, ?, ?)}";
        try {
            CallableStatement statement = sqlConnection.prepareCall(query);
            statement.setDouble(1, amount);
            statement.setString(2, description);
            statement.setTimestamp(3, sqlDate);
            statement.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Connection getSqlConnection() {
        return sqlConnection;
    }

    public MongoCollection getMongoConnection()
    {
        return mongoConnection;
    }
}