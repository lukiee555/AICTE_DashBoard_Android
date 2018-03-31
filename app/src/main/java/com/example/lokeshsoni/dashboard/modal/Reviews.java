package com.example.lokeshsoni.dashboard.modal;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by lokeshsoni on 30/03/18.
 */

public class Reviews implements Serializable {

    private String id = "";
    private String initativeId = "";

    private HashMap<String,String> paramRatings = new HashMap<>();
    private Beneficiary beneficiary = new Beneficiary();


    public Reviews(String id, String initativeId, HashMap<String,String> paramRatings, Beneficiary beneficiary) {
        this.id = id;
        this.initativeId = initativeId;
        this.paramRatings = paramRatings;
        this.beneficiary = beneficiary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitativeId() {
        return initativeId;
    }

    public void setInitativeId(String initativeId) {
        this.initativeId = initativeId;
    }

    public HashMap<String, String> getParamRatings() {
        return paramRatings;
    }

    public void setParamRatings(HashMap<String, String> paramRatings) {
        this.paramRatings = paramRatings;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "id='" + id + '\'' +
                ", initativeId='" + initativeId + '\'' +
                ", paramRatings=" + paramRatings +
                ", beneficiary=" + beneficiary +
                '}';
    }
}
