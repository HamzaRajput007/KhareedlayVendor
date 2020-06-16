package com.example.khareedlayvendor.Models.Models_Products;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Model_Products_Details implements Serializable {
   Long id;
   String type;
   String name;
   String slug;
   String status;
   boolean featured;
   String description;
   String short_description;
   String link;
   String price;
   String regular_price;
   String sale_price;
   int stock_quantity;
   String stock_status;
   boolean manage_stock;
   String weight;
   String length;
   String width;
   String height;
   String dimensions;
   String average_rating;
   boolean reviews_allowed;
   @SerializedName("images")
   ArrayList<String> arrayList_images;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSlug() {
      return slug;
   }

   public void setSlug(String slug) {
      this.slug = slug;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public boolean isFeatured() {
      return featured;
   }

   public void setFeatured(boolean featured) {
      this.featured = featured;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getShort_description() {
      return short_description;
   }

   public void setShort_description(String short_description) {
      this.short_description = short_description;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public String getPrice() {
      return price;
   }

   public void setPrice(String price) {
      this.price = price;
   }

   public String getRegular_price() {
      return regular_price;
   }

   public void setRegular_price(String regular_price) {
      this.regular_price = regular_price;
   }

   public String getSale_price() {
      return sale_price;
   }

   public void setSale_price(String sale_price) {
      this.sale_price = sale_price;
   }

   public int getStock_quantity() {
      return stock_quantity;
   }

   public void setStock_quantity(int stock_quantity) {
      this.stock_quantity = stock_quantity;
   }

   public String getStock_status() {
      return stock_status;
   }

   public void setStock_status(String stock_status) {
      this.stock_status = stock_status;
   }

   public boolean isManage_stock() {
      return manage_stock;
   }

   public void setManage_stock(boolean manage_stock) {
      this.manage_stock = manage_stock;
   }

   public String getWeight() {
      return weight;
   }

   public void setWeight(String weight) {
      this.weight = weight;
   }

   public String getLength() {
      return length;
   }

   public void setLength(String length) {
      this.length = length;
   }

   public String getWidth() {
      return width;
   }

   public void setWidth(String width) {
      this.width = width;
   }

   public String getHeight() {
      return height;
   }

   public void setHeight(String height) {
      this.height = height;
   }

   public String getDimensions() {
      return dimensions;
   }

   public void setDimensions(String dimensions) {
      this.dimensions = dimensions;
   }

   public String getAverage_rating() {
      return average_rating;
   }

   public void setAverage_rating(String average_rating) {
      this.average_rating = average_rating;
   }

   public boolean isReviews_allowed() {
      return reviews_allowed;
   }

   public void setReviews_allowed(boolean reviews_allowed) {
      this.reviews_allowed = reviews_allowed;
   }

   public ArrayList<String> getArrayList_images() {
      return arrayList_images;
   }

   public void setArrayList_images(ArrayList<String> arrayList_images) {
      this.arrayList_images = arrayList_images;
   }
}
