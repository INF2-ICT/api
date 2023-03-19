package com.quintor.api.service;

import com.quintor.api.util.RelationalDatabaseUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.CallableStatement;


@Service
public class UploadService {
    private final Connection connection;

    public UploadService() throws SQLException {
        this.connection = RelationalDatabaseUtil.getConnection();
    }

    public void uploadXML (MultipartFile xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml.getInputStream());

            String reference = document.getElementsByTagName("reference").item(0).getTextContent();
            String account = document.getElementsByTagName("account").item(0).getTextContent();

            int day = Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(0,2));
            int month = Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(2,4))-1;
            int year = 100 + Integer.parseInt(document.getElementsByTagName("date").item(0).getTextContent().substring(4));
            Date date = new Date(year, month, day);

            double startingBalance = Double.parseDouble(document.getElementsByTagName("amount").item(0).getTextContent().replace(",","."));
            int endOfNode = document.getElementsByTagName("amount").getLength()-1;
            double closingBalance = Double.parseDouble(document.getElementsByTagName("amount").item(endOfNode).getTextContent().replace(",","."));

            uploadMT940(reference, account, date, startingBalance, closingBalance);

            for (int i = 0; i < document.getElementsByTagName("amount").getLength()-2; i++) {
                Date valueDate; // TODO parse to date
                Date entryDate; // TODO parse to date
                String type = document.getElementsByTagName("creditDebit").item(i).getTextContent();
                Double amount = Double.parseDouble(document.getElementsByTagName("amount").item(i+1).getTextContent());
                String code = document.getElementsByTagName("identifierCode").item(i).getTextContent();
                String customerReference = document.getElementsByTagName("customerReference").item(i).getTextContent();
                String bankReference = document.getElementsByTagName("bankReference").item(i).getTextContent();
                String supplementaryDetails = document.getElementsByTagName("supplementaryDetails").item(i).getTextContent();
                uploadTransacion(reference, valueDate, entryDate, date, type, amount, code, customerReference, bankReference, supplementaryDetails);
            }

        } catch (IOException | ParserConfigurationException | SAXException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void uploadMT940(String reference, String account, Date date, double startingBalance, double closingBalance) throws SQLException {
        String query = "{ CALL add_MT940(?, ?, ?, ?, ?)}";

        CallableStatement statement = connection.prepareCall(query);

        statement.setString(1, reference);
        statement.setString(2, account);
        statement.setDate(3, date);
        statement.setDouble(4, startingBalance);
        statement.setDouble(5, closingBalance);

        statement.executeQuery();
    }

    public void uploadTransacion(String reference,
                                 Date valueDate,
                                 Date entryDate,
                                 String type,
                                 Double amount,
                                 String code,
                                 String customerReference,
                                 String bankReference,
                                 String supplementaryDetails) throws SQLException {

        String query = "{ CALL add_transaction(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        CallableStatement statement = connection.prepareCall(query);

        statement.setString(1, reference);
        statement.setDate(2, valueDate);
        statement.setDate(3, entryDate);
        statement.setString(4, type);
        statement.setDouble(5, amount);
        statement.setString(6, code);
        statement.setString(7, customerReference);
        statement.setString(8, bankReference);
        statement.setString(9, supplementaryDetails);

        statement.executeQuery();
    }

    public Connection getConnection() {
        return connection;
    }
}