package com.example.lokeshsoni.dashboard.modal;

/**
 * Created by hp on 27-03-2018.
 */

public class InitiativesCardView {
    private String init_card_view_name;
    private int init_card_view_image;
    private  String desc;
    private String id;

public  InitiativesCardView(){}

public InitiativesCardView(String id,String init_card_view_name, int init_card_view_image,String desc ){
    this.init_card_view_name = init_card_view_name;
    this.init_card_view_image = init_card_view_image;
    this.desc = desc;
    this.id = id;
}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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