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

    private double amount_in_euro;

    private String transactionCode;

    private String owner_reference;

    private String beneficiary_reference;

    private String additional_description;

    public TransactionModel(int id, String transaction_reference, LocalDate value_date, LocalDate entry_date, TransactionType transactionType, double amount_in_euro, String transactionCode, String owner_reference, String beneficiary_reference, String additional_description) {
        this.id = id;
        this.transaction_reference = transaction_reference;
        this.value_date = value_date;
        this.entry_date = entry_date;
        this.transactionType = transactionType;
        this.amount_in_euro = amount_in_euro;
        this.transactionCode = transactionCode;
        this.owner_reference = owner_reference;
        this.beneficiary_reference = beneficiary_reference;
        this.additional_description = additional_description;
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

    public double getAmount_in_euro() {
        return this.amount_in_euro;
    }

    public void setAmount_in_euro(double amount_in_euro) {
        this.amount_in_euro = amount_in_euro;
    }

    public String getTransactionCode() {
        return this.transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
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

    public String getAdditional_description() {
        return this.additional_description;
    }

    public void setAdditional_description(String additional_description) {
        this.additional_description = additional_description;
    }
}
