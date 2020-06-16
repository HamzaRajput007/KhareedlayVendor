package com.example.khareedlayvendor.Models.Model_Orders;

import com.google.gson.annotations.SerializedName;

public class Model_ItemsDetails {
    @SerializedName("id")
    Long item_Id;
    Long variation_id;
    String name;
    int quantity;
    String sub_total;
    String total;
    String type;

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public Long getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(Long variation_id) {
        this.variation_id = variation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
