package com.example.library.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FineCalculatorTest {

    @Test
    void calculate_ShouldReturn10PerDay_WhenOverdueIsLessOrEqual7Days() {
        // Arrange
        FineCalculator calculator = new FineCalculator();
        int overdueDays = 5;

        // Act
        double actualFine = calculator.calculate(overdueDays);

        // Assert
        assertEquals(50.0, actualFine);
    }

    @Test
    void calculate_ShouldReturn20PerDay_WhenOverdueIsMoreThan7Days() {
        // Arrange
        FineCalculator calculator = new FineCalculator();
        int overdueDays = 10;

        // Act
        double actualFine = calculator.calculate(overdueDays);

        // Assert
        // คาดหวัง: 7 วันแรก * 10 = 70, อีก 3 วัน * 20 = 60 -> รวม 130
        assertEquals(130.0, actualFine);
    }
}