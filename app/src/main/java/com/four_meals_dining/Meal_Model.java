package com.four_meals_dining;

import com.google.firebase.database.PropertyName;

public class Meal_Model {

    private String meal_name;
    private String price;
    private String meal_imageSrc;

    public Meal_Model(String meal_name, String price, String meal_imageSrc) {
        this.meal_name = meal_name;
        this.price = price;
        this.meal_imageSrc = meal_imageSrc;
    }

    public Meal_Model(){

    }

    public String getMeal_name() {
        return meal_name;
    }

    public String getPrice() {
        return "$" + price;
    }

    public String getMeal_imageSrc() {
        return meal_imageSrc;
    }

}
