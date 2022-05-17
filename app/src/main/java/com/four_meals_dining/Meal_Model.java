package com.four_meals_dining;

import com.google.firebase.database.PropertyName;
import com.google.firebase.firestore.DocumentId;

public class Meal_Model {

    private String meal_name;
    private String price;
    private String meal_imageSrc;
    private String documentID;
    private int Count;

    public Meal_Model(String meal_name, String price, String meal_imageSrc, int Count) {
        this.meal_name = meal_name;
        this.price = price;
        this.meal_imageSrc = meal_imageSrc;
        this.Count= Count;
    }

    public Meal_Model(){

    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public String getPrice() {
        return price;
    }

    public int getCount() {
        return Count;
    }

    public String getMeal_imageSrc() {
        return meal_imageSrc;
    }

}
