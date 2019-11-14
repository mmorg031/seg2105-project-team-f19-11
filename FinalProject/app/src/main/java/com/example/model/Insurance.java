package com.example.model;

import androidx.annotation.NonNull;

public enum Insurance{
    OHIP("OHIP"),
    UHIP("UHIP"),
    PrivateInsurance("Private Insurance"),
    NoInsurance("No Insurance");

    private String str;

    private Insurance(String str){
        this.str=str;
    }

    @NonNull
    @Override
    public String toString() {
        return str;
    }
}
