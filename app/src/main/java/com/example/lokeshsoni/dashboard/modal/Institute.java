package com.example.lokeshsoni.dashboard.modal;

/**
 * Created by lokeshsoni on 31/03/18.
 */

public class Institute {
    String name;
    String email;
    public Institute(String name,String email){
        this.name = name;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
