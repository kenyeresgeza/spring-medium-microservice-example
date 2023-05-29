package com.example.microservice.limit;


public class LimitConfiguration {

    private int maximum;
    private int minimum;

    protected LimitConfiguration() {
    }

    public int getMaximum() {
        return maximum;
    }
    public int getMinimum() {
        return minimum;
    }
    public LimitConfiguration(int maximum, int minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

}
