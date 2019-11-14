package com.example.model;

import androidx.annotation.NonNull;

public enum PaymentMethods{
    Cash("Cash"),
    Debit("Debit"),
    Credit("Credit");

    private String str;

    private PaymentMethods(String str){
        this.str=str;
    }

    @NonNull
    @Override
    public String toString() {
        return str;
    }
}
