package com.example.library.service;

import org.springframework.stereotype.Service;

@Service
public class FineCalculator {

    public double calculate(int overdueDays) {
        if(overdueDays <= 7) {
            return overdueDays * 10.0;
        }else{
            int extraDays = overdueDays - 7;
            return (7 * 10.0) + (extraDays * 20.0);
        }
        
    }
}