package com.weather.data.service;

public class WeatherDataStatistics {
    private long totalRecords;
    private Double avgTemperature;
    private Double maxTemperature;
    private Double minTemperature;
    private Double avgHumidity;
    private long rainCount;
    private long snowCount;
    private long thunderCount;
    private long fogCount;

    public WeatherDataStatistics() {
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(Double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public long getRainCount() {
        return rainCount;
    }

    public void setRainCount(long rainCount) {
        this.rainCount = rainCount;
    }

    public long getSnowCount() {
        return snowCount;
    }

    public void setSnowCount(long snowCount) {
        this.snowCount = snowCount;
    }

    public long getThunderCount() {
        return thunderCount;
    }

    public void setThunderCount(long thunderCount) {
        this.thunderCount = thunderCount;
    }

    public long getFogCount() {
        return fogCount;
    }

    public void setFogCount(long fogCount) {
        this.fogCount = fogCount;
    }

    @Override
    public String toString() {
        return "WeatherDataStatistics{" +
                "totalRecords=" + totalRecords +
                ", avgTemperature=" + String.format("%.2f", avgTemperature) +
                ", maxTemperature=" + String.format("%.2f", maxTemperature) +
                ", minTemperature=" + String.format("%.2f", minTemperature) +
                ", avgHumidity=" + String.format("%.2f", avgHumidity) +
                ", rainCount=" + rainCount +
                ", snowCount=" + snowCount +
                ", thunderCount=" + thunderCount +
                ", fogCount=" + fogCount +
                '}';
    }
}
