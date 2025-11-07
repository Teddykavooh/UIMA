package com.teddykavooh.uima.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class BmiCalculator {

    /**
     * Calculates the Body Mass Index (BMI).
     *
     * @param weightKg The weight in kilograms.
     * @param heightM  The height in meters.
     * @return The calculated BMI, or 0 if the height is invalid.
     */
    public static double calculate(double weightKg, double heightM) {
        if (heightM <= 0) {
            return 0.0; // Avoid division by zero
        }
        return weightKg / (heightM * heightM);
    }

    /**
     * Get Analysis
     * @param bmi The BMI value.
     * @return The analysis based on the BMI value.
     */
    public String getAnalysis(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    /**
     * Calculates the age from a date of birth string.
     * @param dobString The date of birth in "yyyy-MM-dd" format.
     * @return The calculated age in years, or 0 if parsing fails.
     */
    public static int calculateAge(String dobString) {
        if (dobString == null || dobString.isEmpty()) {
            return 0;
        }
        try {
            LocalDate birthDate = LocalDate.parse(dobString);
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        } catch (DateTimeParseException e) {
            // Handle the case where the string is not in the expected format
            e.printStackTrace();
            return 0;
        }
    }
}
