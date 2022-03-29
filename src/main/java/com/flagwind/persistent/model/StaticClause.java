package com.flagwind.persistent.model;

public class StaticClause implements Clause {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}