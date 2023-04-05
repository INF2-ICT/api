package com.quintor.api.model;

import com.quintor.api.enums.TransactionType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class TransactionModel {

    @Id
    private int id;

    private String transaction_reference;

    private LocalDate value_date;

    private LocalDate entry_date;

    private TransactionType transactionType;

    private String fund_code;

    private double amount_in_euro;

    private String identifier_code;

    private String owner_reference;

    private String beneficiary_reference;

    private String supplementary_details;

    private String line1;
    private String line2;
    private String line3;
    private String line4;
    private String line5;
    private String line6;
    private String user_comment;

    public TransactionModel(int id, String transaction_reference, LocalDate value_date, LocalDate entry_date, TransactionType transactionType, String fund_code, double amount_in_euro, String identifier_code, String owner_reference, String beneficiary_reference, String supplementary_details, String line1, String line2, String line3, String line4, String line5, String line6, String user_comment) {
        this.id = id;
        this.transaction_reference = transaction_reference;
        this.value_date = value_date;
        this.entry_date = entry_date;
        this.transactionType = transactionType;
        this.fund_code = fund_code;
        this.amount_in_euro = amount_in_euro;
        this.identifier_code = identifier_code;
        this.owner_reference = owner_reference;
        this.beneficiary_reference = beneficiary_reference;
        this.supplementary_details = supplementary_details;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.line5 = line5;
        this.line6 = line6;
        this.user_comment = user_comment;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction_reference() {
        return this.transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }

    public LocalDate getValue_date() {
        return this.value_date;
    }

    public void setValue_date(LocalDate value_date) {
        this.value_date = value_date;
    }

    public LocalDate getEntry_date() {
        return this.entry_date;
    }

    public void setEntry_date(LocalDate entry_date) {
        this.entry_date = entry_date;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getFund_code() {
        return this.fund_code;
    }

    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;
    }

    public double getAmount_in_euro() {
        return this.amount_in_euro;
    }

    public void setAmount_in_euro(double amount_in_euro) {
        this.amount_in_euro = amount_in_euro;
    }

    public String getIdentifier_code() {
        return this.identifier_code;
    }

    public void setIdentifier_code(String identifier_code) {
        this.identifier_code = identifier_code;
    }

    public String getOwner_reference() {
        return this.owner_reference;
    }

    public void setOwner_reference(String owner_reference) {
        this.owner_reference = owner_reference;
    }

    public String getBeneficiary_reference() {
        return this.beneficiary_reference;
    }

    public void setBeneficiary_reference(String beneficiary_reference) {
        this.beneficiary_reference = beneficiary_reference;
    }

    public String getSupplementary_details() {
        return this.supplementary_details;
    }

    public void setSupplementary_details(String supplementary_details) {
        this.supplementary_details = supplementary_details;
    }

    public String getLine1() {
        return this.line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return this.line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getLine5() {
        return this.line5;
    }

    public void setLine5(String line5) {
        this.line5 = line5;
    }

    public String getLine6() {
        return this.line6;
    }

    public void setLine6(String line6) {
        this.line6 = line6;
    }

    public String getUser_comment() {
        return this.user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }
}
