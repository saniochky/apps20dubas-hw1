package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;


public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private final double minPossibleTemperature = -273.0;
    private int seriesLength = 0;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[0];
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length > 0) {
            Arrays.sort(temperatureSeries);

            if (temperatureSeries[0] < this.minPossibleTemperature) {
                throw new InputMismatchException("Values from temperature series shouldn`t be less than -273C");
            }

            this.seriesLength = temperatureSeries.length;
            this.temperatureSeries = new double[this.seriesLength];
            this.temperatureSeries = Arrays.copyOfRange(temperatureSeries, 0, this.seriesLength);
        } else {
            this.temperatureSeries = new double[0];
        }
    }

    private void checkTemperatureSeriesForEmptiness() {
        if (this.seriesLength == 0) {
            throw new IllegalArgumentException("The temperature series shouldn`t be empty");
        }
    }

    private void increaseSize() {
        double[] newTemperatureSeries = new double[this.temperatureSeries.length * 2];
        System.arraycopy(this.temperatureSeries, 0, newTemperatureSeries, 0, this.seriesLength);
        this.temperatureSeries = newTemperatureSeries;
    }

    public double average() {
        this.checkTemperatureSeriesForEmptiness();
        double temperatureSum = 0;

        for (int i = 0; i < this.seriesLength; i++) {
            temperatureSum += this.temperatureSeries[i];
        }

        return temperatureSum / this.seriesLength;
    }

    public double deviation() {
        this.checkTemperatureSeriesForEmptiness();
        double mean = this.average();
        double sum = 0;

        for (int i = 0; i < this.seriesLength; i++) {
            sum += (this.temperatureSeries[i] - mean) * (this.temperatureSeries[i] - mean);
        }

        return Math.sqrt(sum / this.seriesLength);
    }

    public double min() {
        this.checkTemperatureSeriesForEmptiness();
        double min = this.temperatureSeries[0];

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] < min) {
                min = this.temperatureSeries[i];
            }
        }

        return min;
    }

    public double max() {
        this.checkTemperatureSeriesForEmptiness();
        double max = this.temperatureSeries[0];

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] > max) {
                max = this.temperatureSeries[i];
            }
        }

        return max;
    }

    public double findTempClosestToZero() {
        return this.findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        double closestToValue = Math.abs(tempValue - this.temperatureSeries[0]);
        double value = 0.0;

        for (int i = 0; i < this.seriesLength; i++) {
            if (Math.abs(tempValue - this.temperatureSeries[i]) < closestToValue) {
                closestToValue = Math.abs(tempValue - this.temperatureSeries[i]);
                value = this.temperatureSeries[i];
            } else if (Math.abs(tempValue - this.temperatureSeries[i]) == closestToValue) {
                if (tempValue - this.temperatureSeries[i] < 0) {
                    closestToValue = Math.abs(tempValue - this.temperatureSeries[i]);
                    value = this.temperatureSeries[i];
                }
            }
        }

        return value;
    }

    public double[] findTempsLessThen(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        int numberOfLessTempValues = 0;
        int iter = 0;

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] < tempValue) {
                numberOfLessTempValues += 1;
            }
        }

        double[] lessTemperatureSeries = new double[numberOfLessTempValues];

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] < tempValue) {
                lessTemperatureSeries[iter] = this.temperatureSeries[i];
                iter += 1;
            }
        }

        return lessTemperatureSeries;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        this.checkTemperatureSeriesForEmptiness();
        int numberOfGreaterTempValues = 0;
        int iter = 0;

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] > tempValue) {
                numberOfGreaterTempValues += 1;
            }
        }

        double[] greaterTemperatureSeries = new double[numberOfGreaterTempValues];

        for (int i = 0; i < this.seriesLength; i++) {
            if (this.temperatureSeries[i] > tempValue) {
                greaterTemperatureSeries[iter] = this.temperatureSeries[i];
                iter += 1;
            }
        }

        return greaterTemperatureSeries;
    }

    public TempSummaryStatistics summaryStatistics() {
        this.checkTemperatureSeriesForEmptiness();

        return new TempSummaryStatistics(this.average(), this.deviation(), this.min(), this.max());
    }

    public int addTemps(double... temps) {
        for (double temp: temps) {
            if (temp < this.minPossibleTemperature) {
                throw new InputMismatchException("Values from temperature series shouldn`t be less than -273C");
            }
        }

        while (this.temperatureSeries.length - this.seriesLength < temps.length) {
            this.increaseSize();
        }

        System.arraycopy(temps, 0, this.temperatureSeries, this.seriesLength, temps.length);

        this.seriesLength += temps.length;
        return this.seriesLength;
    }
}
