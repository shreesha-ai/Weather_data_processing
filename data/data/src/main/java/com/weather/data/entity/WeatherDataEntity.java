package com.weather.data.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime datetimeUtc;
    
    @Column(length = 50)
    private String conditions;
    
    @Column
    private Double dewptm;
    
    @Column
    private Integer fog;
    
    @Column
    private Integer hail;
    
    @Column
    private Double heatindexm;
    
    @Column
    private Integer humidity;
    
    @Column
    private Double precipm;
    
    @Column
    private Double pressurem;
    
    @Column
    private Integer rain;
    
    @Column
    private Integer snow;
    
    @Column
    private Double tempm;
    
    @Column
    private Integer thunder;
    
    @Column
    private Integer tornado;
    
    @Column
    private Double visibility;
    
    @Column
    private Integer windDirection;
    
    @Column(length = 10)
    private String windDirectionName;
    
    @Column
    private Double windGustm;
    
    @Column
    private Double windchillm;
    
    @Column
    private Double wspdm;

    // Constructors
    public WeatherDataEntity() {
    }

    public WeatherDataEntity(LocalDateTime datetimeUtc, String conditions, Double dewptm, 
                            Integer fog, Integer hail, Double heatindexm, Integer humidity,
                            Double precipm, Double pressurem, Integer rain, Integer snow,
                            Double tempm, Integer thunder, Integer tornado, Double visibility,
                            Integer windDirection, String windDirectionName, Double windGustm,
                            Double windchillm, Double wspdm) {
        this.datetimeUtc = datetimeUtc;
        this.conditions = conditions;
        this.dewptm = dewptm;
        this.fog = fog;
        this.hail = hail;
        this.heatindexm = heatindexm;
        this.humidity = humidity;
        this.precipm = precipm;
        this.pressurem = pressurem;
        this.rain = rain;
        this.snow = snow;
        this.tempm = tempm;
        this.thunder = thunder;
        this.tornado = tornado;
        this.visibility = visibility;
        this.windDirection = windDirection;
        this.windDirectionName = windDirectionName;
        this.windGustm = windGustm;
        this.windchillm = windchillm;
        this.wspdm = wspdm;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPressurem() {
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
        return "WeatherDataEntity{" +
                "id=" + id +
                ", datetimeUtc=" + datetimeUtc +
                ", conditions='" + conditions + '\'' +
                ", tempm=" + tempm +
                ", humidity=" + humidity +
                '}';
    }
}
