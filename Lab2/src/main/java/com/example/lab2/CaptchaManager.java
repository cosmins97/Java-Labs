package com.example.lab2;

import java.util.Random;

public class CaptchaManager {
    private int firstNumber;
    private int secondNumber;
    private int Sum;

    public CaptchaManager(){

    }

    public void generateNumbers(){
        Random rand = new Random();

        firstNumber = rand.nextInt(1000);
        secondNumber = rand.nextInt(1000);
        Sum = firstNumber + secondNumber;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public int getSum() {
        return Sum;
    }
}
