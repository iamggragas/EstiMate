package com.example.main.Utilities;

public class Listings {
    double size;            // Size in square feet
    int bedrooms;           // Number of bedrooms
    int quality;            // Quality scale (1 to 10)
    double age;             // Age of the house in years
    double price;

    String houseName, houseAddress;
    private String key;

    public Listings(double size, int bedrooms, int quality, double age, double price, String houseName, String houseAddress) {
        this.size = size;
        this.bedrooms = bedrooms;
        this.quality = quality;
        this.age = age;
        this.price = price;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
    }

    public Listings() {

    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
