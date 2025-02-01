package org.example;

public class Timer {
    protected static double Time = 0.0;

    public static void incrementTime() {
        Time += 1.0;
    }

    public static double getTime() {
        return Time;
    }
}
