package com.example.lokeshsoni.dashboard.modal;

/**
 * Created by hp on 27-03-2018.
 */

public class InitiativesCardView {
    private String init_card_view_name;
    private int init_card_view_image;


public  InitiativesCardView(){}

public InitiativesCardView(String init_card_view_name, int init_card_view_image){
    this.init_card_view_name = init_card_view_name;
    this.init_card_view_image = init_card_view_image;
}

    public String getInit_card_view_name() {
        return init_card_view_name;
    }

    public void setInit_card_view_name(String init_card_view_name) {
        this.init_card_view_name = init_card_view_name;
    }

    public int getInit_card_view_image() {
        return init_card_view_image;
    }

    public void setInit_card_view_image(int init_card_view_image) {
        this.init_card_view_image = init_card_view_image;
    }
}