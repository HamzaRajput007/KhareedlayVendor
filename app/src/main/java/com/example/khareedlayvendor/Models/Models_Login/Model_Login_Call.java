package com.example.khareedlayvendor.Models.Models_Login;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Model_Login_Call {

    @SerializedName("roles")
    ArrayList<String> arrayList;
    @SerializedName("data")
    Model_Login_UserDetails model_login_userDetails;

    public Model_Login_UserDetails getModel_login_userDetails() {
        return model_login_userDetails;
    }

    public void setModel_login_userDetails(Model_Login_UserDetails model_login_userDetails) {
        this.model_login_userDetails = model_login_userDetails;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
