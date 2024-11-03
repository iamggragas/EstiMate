package com.example.main.Utilities;

public class House {
    double size;            // Size in square feet
    int bedrooms;           // Number of bedrooms
    int quality;            // Quality scale (1 to 10)
    double age;             // Age of the house in years
    double price;           // Price of the house

    public House(double size, int bedrooms, int quality, double age, double price) {
        this.size = size;
        this.bedrooms = bedrooms;
        this.quality = quality;
        this.age = age;
        this.price = price;
    }
}
