package com.weather.data.specification;

import com.weather.data.entity.WeatherDataEntity;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class WeatherDataSpecification {

    public static Specification<WeatherDataEntity> hasTemperatureRange(Double minTemp, Double maxTemp) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (minTemp != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("tempm"), minTemp));
            }
            if (maxTemp != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("tempm"), maxTemp));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasHumidityRange(Integer minHumidity, Integer maxHumidity) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (minHumidity != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("humidity"), minHumidity));
            }
            if (maxHumidity != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("humidity"), maxHumidity));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (startDate != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("datetimeUtc"), startDate));
            }
            if (endDate != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("datetimeUtc"), endDate));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasCondition(String condition) {
        return (root, query, cb) -> {
            if (condition == null || condition.isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("conditions")), condition.toLowerCase());
        };
    }

    public static Specification<WeatherDataEntity> hasPressureRange(Double minPressure, Double maxPressure) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (minPressure != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("pressurem"), minPressure));
            }
            if (maxPressure != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("pressurem"), maxPressure));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasVisibilityRange(Double minVis, Double maxVis) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (minVis != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("visibility"), minVis));
            }
            if (maxVis != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("visibility"), maxVis));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasWindSpeedRange(Double minWind, Double maxWind) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (minWind != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("wspdm"), minWind));
            }
            if (maxWind != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("wspdm"), maxWind));
            }
            
            return predicate;
        };
    }

    public static Specification<WeatherDataEntity> hasWeatherEvent(String eventType) {
        return (root, query, cb) -> {
            if (eventType == null) {
                return cb.conjunction();
            }
            String event = eventType.toLowerCase();
            if ("rain".equals(event)) {
                return cb.equal(root.get("rain"), 1);
            } else if ("snow".equals(event)) {
                return cb.equal(root.get("snow"), 1);
            } else if ("thunder".equals(event)) {
                return cb.equal(root.get("thunder"), 1);
            } else if ("fog".equals(event)) {
                return cb.equal(root.get("fog"), 1);
            } else if ("hail".equals(event)) {
                return cb.equal(root.get("hail"), 1);
            } else {
                return cb.conjunction();
            }
        };
    }
}
