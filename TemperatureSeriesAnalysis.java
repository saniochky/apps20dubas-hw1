package ua.edu.ucu.tempseries;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import java.util.Arrays;
import java.util.InputMismatchException;


public class TemperatureSeriesAnalysis {
    private double temperatureSeries[];

    private void checkTemperatureSeriesForEmptiness() {
        if (this.temperatureSeries.length == 0) {
            throw new IllegalArgumentException("The temperature series shouldn`t be empty");
        }
    }

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[0];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double value: temperatureSeries) {
            if (value < -273) {
                throw new InputMismatchException("Values from temperature series shouldn`t be less than -273C");
            }
        }

        this.temperatureSeries = temperatureSeries;
        Arrays.sort(this.temperatureSeries);
    }

    public double average() {
        this.checkTemperatureSeriesForEmptiness();
        double temperatureSum = 0;

        for (double value: this.temperatureSeries) {
            temperatureSum += value;
        }

        return temperatureSum / this.temperatureSeries.length;
    }

    public double deviation() {
        this.checkTemperatureSeriesForEmptiness();
        return 0;
    }

    public double min() {
        this.checkTemperatureSeriesForEmptiness();
        return this.temperatureSeries[0];
    }

    public double max() {
        this.checkTemperatureSeriesForEmptiness();
        return this.temperatureSeries[this.temperatureSeries.length - 1];
    }

    public double findTempClosestToZero() {
        return this.findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        double closestToValue = Math.abs(tempValue - this.temperatureSeries[0]);

        for (double value: this.temperatureSeries) {
            if (Math.abs(tempValue - value) <= closestToValue) {
                closestToValue = Math.abs(tempValue - value);
            }
        }

        return closestToValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        int breakPoint = 0;

        for (int i = 0; i < this.temperatureSeries.length; i++) {
            if (tempValue >= this.temperatureSeries[i]) {
                breakPoint = i;
                break;
            }
        }

        return Arrays.copyOfRange(this.temperatureSeries, 0, breakPoint);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        int breakPoint = this.temperatureSeries.length - 1;

        for (int i = this.temperatureSeries.length - 1; i >= 0; i--) {
            if (tempValue > this.temperatureSeries[i]) {
                breakPoint = i + 1;
                break;
            }
        }

        return Arrays.copyOfRange(this.temperatureSeries, breakPoint, this.temperatureSeries.length);
    }

    public TempSummaryStatistics summaryStatistics() {
        this.checkTemperatureSeriesForEmptiness();
        return null;
    }

    public int addTemps(double... temps) {
        return 0;
    }
}
