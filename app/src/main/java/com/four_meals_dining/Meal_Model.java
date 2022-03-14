package com.four_meals_dining;

public class Meal_Model {

    private String meal_name;
    private double price;
    private int meal_imageSrc;

    public Meal_Model(String meal_name, double price, int meal_imageSrc) {
        this.meal_name = meal_name;
        this.price = price;
        this.meal_imageSrc = meal_imageSrc;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMeal_imageSrc() {
        return meal_imageSrc;
    }

    public void setMeal_imageSrc(int meal_imageSrc) {
        this.meal_imageSrc = meal_imageSrc;
    }
}
