package com.example.khareedlayvendor.Models.Model_Orders;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Model_OrderDetails {

    int id;
    String key;
    String formated_total;
    Long sub_total;
    String total;
    Long discount;
    String payment_method;
    String payment_title;
    String trans_id;
    String status;
    String shipping_method;
    @SerializedName("items")
    ArrayList<Model_ItemsDetails> arrayList_itemsDetails;
    @SerializedName("customer")
    Model_CustomerDetails model_customerDetails;
    @SerializedName("date")
    Model_OrderDateTime model_orderDateTime;

    public Model_OrderDateTime getModel_orderDateTime() {
        return model_orderDateTime;
    }

    public void setModel_orderDateTime(Model_OrderDateTime model_orderDateTime) {
        this.model_orderDateTime = model_orderDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormated_total() {
        return formated_total;
    }

    public void setFormated_total(String formated_total) {
        this.formated_total = formated_total;
    }

    public Long getSub_total() {
        return sub_total;
    }

    public void setSub_total(Long sub_total) {
        this.sub_total = sub_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_title() {
        return payment_title;
    }

    public void setPayment_title(String payment_title) {
        this.payment_title = payment_title;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public ArrayList<Model_ItemsDetails> getArrayList_itemsDetails() {
        return arrayList_itemsDetails;
    }

    public void setArrayList_itemsDetails(ArrayList<Model_ItemsDetails> arrayList_itemsDetails) {
        this.arrayList_itemsDetails = arrayList_itemsDetails;
    }

    public Model_CustomerDetails getModel_customerDetails() {
        return model_customerDetails;
    }

    public void setModel_customerDetails(Model_CustomerDetails model_customerDetails) {
        this.model_customerDetails = model_customerDetails;
    }
}
