package com.quintor.api.model;

import com.quintor.api.enums.TransactionType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class TransactionModel {
    @Id
    private int id;
    @NonNull
    private String transaction_reference;
    @NonNull
    private LocalDate value_date;
    @NonNull
    private TransactionType transactionType;
    @NonNull
    private double amount_in_euro;

    public TransactionModel(int id, String transaction_reference, LocalDate value_date, TransactionType transactionType, double amount_in_euro) {
        this.id = id;
        this.transaction_reference = transaction_reference;
        this.value_date = value_date;
        this.transactionType = transactionType;
        this.amount_in_euro = amount_in_euro;
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
}