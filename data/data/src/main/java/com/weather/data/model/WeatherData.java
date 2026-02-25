package com.weather.data.model;

import java.time.LocalDateTime;

public class WeatherData {
    private LocalDateTime datetimeUtc;
    private String conditions;
    private Double dewptm;
    private Integer fog;
    private Integer hail;
    private Double heatindexm;
    private Integer humidity;
    private Double precipm;
    private Double pressurem;
    private Integer rain;
    private Integer snow;
    private Double tempm;
    private Integer thunder;
    private Integer tornado;
    private Double visibility;
    private Integer windDirection;
    private String windDirectionName;
    private Double windGustm;
    private Double windchillm;
    private Double wspdm;

    public WeatherData() {
    }

    public LocalDateTime getDatetimeUtc() {
        return datetimeUtc;
    }

    public void setDatetimeUtc(LocalDateTime datetimeUtc) {
        this.datetimeUtc = datetimeUtc;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Double getDewptm() {
        return dewptm;
    }

    public void setDewptm(Double dewptm) {
        this.dewptm = dewptm;
    }

    public Integer getFog() {
        return fog;
    }

    public void setFog(Integer fog) {
        this.fog = fog;
    }

    public Integer getHail() {
        return hail;
    }

    public void setHail(Integer hail) {
        this.hail = hail;
    }

    public Double getHeatindexm() {
        return heatindexm;
    }

    public void setHeatindexm(Double heatindexm) {
        this.heatindexm = heatindexm;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPrecipm() {
        return precipm;
    }

    public void setPrecipm(Double precipm) {
        this.precipm = precipm;
    }

    public Double getPerssurem() {
        return pressurem;
    }

    public void setPressurem(Double pressurem) {
        this.pressurem = pressurem;
    }

    public Integer getRain() {
        return rain;
    }

    public void setRain(Integer rain) {
        this.rain = rain;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public Double getTempm() {
        return tempm;
    }

    public void setTempm(Double tempm) {
        this.tempm = tempm;
    }

    public Integer getThunder() {
        return thunder;
    }

    public void setThunder(Integer thunder) {
        this.thunder = thunder;
    }

    public Integer getTornado() {
        return tornado;
    }

    public void setTornado(Integer tornado) {
        this.tornado = tornado;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindDirectionName() {
        return windDirectionName;
    }

    public void setWindDirectionName(String windDirectionName) {
        this.windDirectionName = windDirectionName;
    }

    public Double getWindGustm() {
        return windGustm;
    }

    public void setWindGustm(Double windGustm) {
        this.windGustm = windGustm;
    }

    public Double getWindchillm() {
        return windchillm;
    }

    public void setWindchillm(Double windchillm) {
        this.windchillm = windchillm;
    }

    public Double getWspdm() {
        return wspdm;
    }

    public void setWspdm(Double wspdm) {
        this.wspdm = wspdm;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "datetimeUtc=" + datetimeUtc +
                ", conditions='" + conditions + '\'' +
                ", tempm=" + tempm +
                ", humidity=" + humidity +
                '}';
    }
}
