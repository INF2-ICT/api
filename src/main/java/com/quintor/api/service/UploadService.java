package com.quintor.api.service;

import com.quintor.api.util.RelationalDatabaseUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.time.LocalDate;

@Service
public class UploadService {
    private final Connection connection;

    public UploadService() throws SQLException {
        this.connection = RelationalDatabaseUtil.getConnection();
    }

    public void uploadXML (MultipartFile xml) throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(xml.getInputStream());

        String query = "{ CALL add_MT940(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = connection.prepareCall(query)) {

            int day = Integer.parseInt(d.getElementsByTagName("date").item(0).getTextContent().substring(0,2));
            int month = Integer.parseInt(d.getElementsByTagName("date").item(0).getTextContent().substring(2,4))-1;
            int year = 100 + Integer.parseInt(d.getElementsByTagName("date").item(0).getTextContent().substring(4));
            System.out.println(day + "-" + month + "-" + year);
            Date date = new Date(year, month, day);

            statement.setString(1, d.getElementsByTagName("reference").item(0).getTextContent());
            statement.setString(2, d.getElementsByTagName("account").item(0).getTextContent());
            statement.setDate(3, date);
            statement.setDouble(4, Double.parseDouble(d.getElementsByTagName("amount").item(0).getTextContent().replace(",",".")));
            int l = d.getElementsByTagName("amount").getLength()-1;
            statement.setDouble(5, Double.parseDouble(d.getElementsByTagName("amount").item(l).getTextContent().replace(",",".")));

            statement.executeQuery();

        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}