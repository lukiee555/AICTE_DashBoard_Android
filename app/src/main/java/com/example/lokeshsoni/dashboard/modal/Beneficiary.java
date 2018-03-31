package com.example.lokeshsoni.dashboard.modal;

import java.io.Serializable;

/**
 * Created by lokeshsoni on 30/03/18.
 */

public class Beneficiary implements Serializable {

            private  String id;
            private  String name;
            private String email;
            private String initiative;

    public Beneficiary() {


    }

    public Beneficiary(String id, String name, String email, String initiative) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.initiative = initiative;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitiative() {
        return initiative;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", initiative='" + initiative + '\'' +
                '}';
    }
}
