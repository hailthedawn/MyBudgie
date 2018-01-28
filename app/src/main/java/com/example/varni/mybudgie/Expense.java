package com.example.varni.mybudgie;

import java.io.Serializable;

/**
 * Created by akash on 1/28/2018.
 */

public class Expense implements Serializable,Entry {
    String source;
    double value;
    String desp;
    String name;

    public Expense(String source, double value, String desp, String name) {
        this.source = source;
        this.value = value;
        this.desp = desp;
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }

    public String getDesp() {
        return desp;
    }

    public String getName() {
        return name;
    }
}
