package com.example.main.Utilities;

import java.util.ArrayList;
import java.util.List;

public class HousePrediction {
    List<House> dataset = new ArrayList<>();

    public void addHouse(House house) {
        dataset.add(house);
    }

    public double predictPrice(double size, int bedrooms, int quality, double age) {
        // Very simple linear regression formula
        double predictedPrice = 0;

        // Let's assume coefficients based on average trends
        double sizeCoefficient = 25000;    // P20,000 per square meter
        double bedroomCoefficient = 30000; // P30,000 per bedroom
        double qualityCoefficient = 50000; // P50,000 for quality
        double ageCoefficient = -25000;      // Cost reduction per year of house age

        predictedPrice += size * sizeCoefficient;
        predictedPrice += bedrooms * bedroomCoefficient;
        predictedPrice += quality * qualityCoefficient;
        predictedPrice += age * ageCoefficient;

        return predictedPrice;
    }
}
