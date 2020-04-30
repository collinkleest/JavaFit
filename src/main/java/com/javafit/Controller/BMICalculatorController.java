package com.javafit.Controller;

import com.javafit.View.BMICalculatorView;
import static java.lang.Math.floor;
import static java.lang.Math.round;

/**
 *
 * @author Connell Boyce and Collin Kleest
 */
public class BMICalculatorController {

    private final BMICalculatorView bmiView;

    /**
     * Constructor
     *
     * @param bmiView BMI View to control
     */
    public BMICalculatorController(BMICalculatorView bmiView) {
        this.bmiView = bmiView;
    }

    /**
     * Calculate the user's BMI = (Weight / Height^2) * 703
     *
     * @param height user height
     * @param weight user weight
     * @return calculated BMI rounded to 2 decimal places
     */
    public double calculateBMI(String height, String weight) {
        double userWeight = Double.parseDouble(weight);

        //Make sense of height since it is stored as decimal in Mongo
        //e.g. 6ft 2in = 6.2
        double userHeight = Double.parseDouble(height);
        double feetInInches = floor(userHeight) * 12;
        double remainingInches = (userHeight - floor(userHeight)) * 10;
        double userHeightInInches = feetInInches + remainingInches;

        double bmi = (userWeight / (userHeightInInches * userHeightInInches)) * 703;

        return round(bmi * 100.0) / 100.0;
    }
}
