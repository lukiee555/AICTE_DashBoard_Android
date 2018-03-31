package com.example.lokeshsoni.dashboard.modal;

/**
 * Created by adi on 30/03/18.
 */

public class Rating {
    String name;
    double positive;
    public Rating(String name, double positive){
        this.name = name;
        this.positive = positive;

    }

    public double getPositive() {
        return positive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public String getName() {
        return name;
    }
    public int getPercentage(){
        double num = (double)positive;
        double res = num/5;
        res = res*100;
        return (int)res;
    }
}

