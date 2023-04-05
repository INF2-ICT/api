package com.quintor.api.service;

import com.quintor.api.enums.TransactionType;
import com.quintor.api.model.TransactionModel;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Test
    public void testGetTransactionJson() throws SQLException {

        // Prepare the expected transaction
        TransactionModel expectedTransaction = new TransactionModel(1, "P140220000000001", LocalDate.of(2014, 2, 20), LocalDate.of(2014, 2, 20), TransactionType.C, null, 1.56, "TRF", "EREF", "00000000001005", "/TRCD/00100/", "/EREF/EV12341REP1231456T1234//CNTP/NL32INGB0000012345/INGBNL2", "A/ING BANK NV INZAKE WEB///REMI/USTD//EV10001REP1000000T1000/", null, null, null, null, null);

        TransactionService actualTransaction = new TransactionService();

        // Check that the actual transaction matches the expected transaction
        assertEquals(expectedTransaction.getId(), actualTransaction.getTransactionJson(1).getId(), "this transaction id is not the same as the one in the database");
        assertEquals(expectedTransaction.getTransaction_reference(), actualTransaction.getTransactionJson(1).getTransaction_reference(), "this transaction reference is not the same as the one in the database");
        assertEquals(expectedTransaction.getValue_date(), actualTransaction.getTransactionJson(1).getValue_date(), "this transaction value date is not the same as the one in the database");
        assertEquals(expectedTransaction.getEntry_date(), actualTransaction.getTransactionJson(1).getEntry_date(), "this transaction entry date is not the same as the one in the database");
        assertEquals(expectedTransaction.getTransactionType(), actualTransaction.getTransactionJson(1).getTransactionType(), "this transaction transaction type is not the same as the one in the database");
        assertEquals(expectedTransaction.getFund_code(), actualTransaction.getTransactionJson(1).getFund_code(), "this transaction fund code is not the same as the one in the database");
        assertEquals(expectedTransaction.getAmount_in_euro(), actualTransaction.getTransactionJson(1).getAmount_in_euro(), "this transaction euro amount is not the same as the one in the database");
        assertEquals(expectedTransaction.getIdentifier_code(), actualTransaction.getTransactionJson(1).getIdentifier_code(), "this transaction identifier code is not the same as the one in the database");
        assertEquals(expectedTransaction.getOwner_reference(), actualTransaction.getTransactionJson(1).getOwner_reference(), "this transaction owner reference is not the same as the one in the database");
        assertEquals(expectedTransaction.getBeneficiary_reference(), actualTransaction.getTransactionJson(1).getBeneficiary_reference(), "this transaction beneficiary reference is not the same as the one in the database");
        assertEquals(expectedTransaction.getSupplementary_details(), actualTransaction.getTransactionJson(1).getSupplementary_details(), "this transaction supplementary details is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine1(), actualTransaction.getTransactionJson(1).getLine1(), "this transaction line1 is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine2(), actualTransaction.getTransactionJson(1).getLine2(), "this transaction line2 is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine3(), actualTransaction.getTransactionJson(1).getLine3(), "this transaction line3 is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine4(), actualTransaction.getTransactionJson(1).getLine4(), "this transaction line4 is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine5(), actualTransaction.getTransactionJson(1).getLine5(), "this transaction line5 is not the same as the one in the database");
        assertEquals(expectedTransaction.getLine6(), actualTransaction.getTransactionJson(1).getLine6(), "this transaction line6 is not the same as the one in the database");
        assertEquals(expectedTransaction.getUser_comment(), actualTransaction.getTransactionJson(1).getUser_comment(), "this transaction user comment is not the same as the one in the database");

    }

}