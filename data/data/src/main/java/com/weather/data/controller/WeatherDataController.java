package com.weather.data.controller;

import com.weather.data.entity.WeatherDataEntity;
import com.weather.data.repository.WeatherDataRepository;
import com.weather.data.specification.WeatherDataSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeatherDataController {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Advanced search with filtering and sorting
     * GET /api/weather/search?minTemp=20&maxTemp=30&sortBy=temperature&sortDir=DESC&page=0&size=100
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchWeatherData(
            @RequestParam(required = false) Double minTemp,
            @RequestParam(required = false) Double maxTemp,
            @RequestParam(required = false) Integer minHumidity,
            @RequestParam(required = false) Integer maxHumidity,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) Double minPressure,
            @RequestParam(required = false) Double maxPressure,
            @RequestParam(required = false) Double minVisibility,
            @RequestParam(required = false) Double maxVisibility,
            @RequestParam(required = false) Double minWindSpeed,
            @RequestParam(required = false) Double maxWindSpeed,
            @RequestParam(required = false) String weatherEvent,
            @RequestParam(defaultValue = "datetime") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Specification<WeatherDataEntity> spec = Specification.where((root, query, cb) -> cb.conjunction());
            
            if (minTemp != null || maxTemp != null) {
                spec = spec.and(WeatherDataSpecification.hasTemperatureRange(minTemp, maxTemp));
            }
            if (minHumidity != null || maxHumidity != null) {
                spec = spec.and(WeatherDataSpecification.hasHumidityRange(minHumidity, maxHumidity));
            }
            if (condition != null && !condition.isEmpty()) {
                spec = spec.and(WeatherDataSpecification.hasCondition(condition));
            }
            if (minPressure != null || maxPressure != null) {
                spec = spec.and(WeatherDataSpecification.hasPressureRange(minPressure, maxPressure));
            }
            if (minVisibility != null || maxVisibility != null) {
                spec = spec.and(WeatherDataSpecification.hasVisibilityRange(minVisibility, maxVisibility));
            }
            if (minWindSpeed != null || maxWindSpeed != null) {
                spec = spec.and(WeatherDataSpecification.hasWindSpeedRange(minWindSpeed, maxWindSpeed));
            }
            if (weatherEvent != null && !weatherEvent.isEmpty()) {
                spec = spec.and(WeatherDataSpecification.hasWeatherEvent(weatherEvent));
            }
            if (startDate != null || endDate != null) {
                LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate, dateTimeFormatter) : null;
                LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate, dateTimeFormatter) : null;
                spec = spec.and(WeatherDataSpecification.hasDateRange(start, end));
            }
            
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(spec, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("totalRecords", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("sortBy", sortBy);
            response.put("sortDirection", sortDir);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get all weather data with pagination and sorting
     * GET /api/weather/all?page=0&size=100&sortBy=temperature&sortDir=DESC
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllWeatherData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "datetimeUtc") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("totalRecords", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("sortBy", sortBy);
            response.put("sortDirection", sortDir);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid sort field: " + sortBy);
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get weather data by ID
     * GET /api/weather/id/{id}
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getWeatherDataById(@PathVariable Long id) {
        Optional<WeatherDataEntity> data = weatherDataRepository.findById(id);
        
        if (data.isPresent()) {
            return ResponseEntity.ok(data.get());
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Weather data not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get weather data by temperature range with sorting
     * GET /api/weather/temperature?minTemp=20&maxTemp=30&sortBy=humidity&sortDir=DESC
     */
    @GetMapping("/temperature")
    public ResponseEntity<Map<String, Object>> getWeatherDataByTemperatureRange(
            @RequestParam Double minTemp,
            @RequestParam Double maxTemp,
            @RequestParam(defaultValue = "tempm") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Specification<WeatherDataEntity> spec = WeatherDataSpecification.hasTemperatureRange(minTemp, maxTemp);
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(spec, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("minTemperature", minTemp);
            response.put("maxTemperature", maxTemp);
            response.put("recordsInRange", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            response.put("sortBy", sortBy);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get weather data by humidity range with sorting
     * GET /api/weather/humidity?minHumidity=40&maxHumidity=80&sortBy=humidity&sortDir=DESC
     */
    @GetMapping("/humidity")
    public ResponseEntity<Map<String, Object>> getWeatherDataByHumidityRange(
            @RequestParam Integer minHumidity,
            @RequestParam Integer maxHumidity,
            @RequestParam(defaultValue = "humidity") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Specification<WeatherDataEntity> spec = WeatherDataSpecification.hasHumidityRange(minHumidity, maxHumidity);
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(spec, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("minHumidity", minHumidity);
            response.put("maxHumidity", maxHumidity);
            response.put("recordsInRange", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            response.put("sortBy", sortBy);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get weather data by condition with sorting
     * GET /api/weather/conditions/Smoke?sortBy=temperature&sortDir=DESC
     */
    @GetMapping("/conditions/{condition}")
    public ResponseEntity<Map<String, Object>> getWeatherDataByCondition(
            @PathVariable String condition,
            @RequestParam(defaultValue = "datetimeUtc") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Specification<WeatherDataEntity> spec = WeatherDataSpecification.hasCondition(condition);
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(spec, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("condition", condition);
            response.put("totalRecords", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            response.put("sortBy", sortBy);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get weather data by weather event
     * GET /api/weather/events/rain?sortBy=temperature&sortDir=DESC
     */
    @GetMapping("/events/{eventType}")
    public ResponseEntity<Map<String, Object>> getWeatherDataByEvent(
            @PathVariable String eventType,
            @RequestParam(defaultValue = "datetimeUtc") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());
            Sort sort = Sort.by(direction, getSortField(sortBy));
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Specification<WeatherDataEntity> spec = WeatherDataSpecification.hasWeatherEvent(eventType);
            Page<WeatherDataEntity> dataPage = weatherDataRepository.findAll(spec, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("eventType", eventType);
            response.put("totalOccurrences", dataPage.getTotalElements());
            response.put("percentage", String.format("%.2f%%", 
                    (dataPage.getTotalElements() / (double) weatherDataRepository.count()) * 100));
            response.put("sortBy", sortBy);
            response.put("data", dataPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Get statistics
     * GET /api/weather/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getWeatherStatistics() {
        List<WeatherDataEntity> allData = weatherDataRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRecords", allData.size());
        
        double avgTemp = allData.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherDataEntity::getTempm)
                .average()
                .orElse(0.0);
        
        double maxTemp = allData.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherDataEntity::getTempm)
                .max()
                .orElse(0.0);
        
        double minTemp = allData.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherDataEntity::getTempm)
                .min()
                .orElse(0.0);
        
        Map<String, Object> tempStats = new HashMap<>();
        tempStats.put("average", String.format("%.2f°C", avgTemp));
        tempStats.put("maximum", String.format("%.2f°C", maxTemp));
        tempStats.put("minimum", String.format("%.2f°C", minTemp));
        stats.put("temperature", tempStats);
        
        double avgHumidity = allData.stream()
                .filter(d -> d.getHumidity() != null)
                .mapToDouble(WeatherDataEntity::getHumidity)
                .average()
                .orElse(0.0);
        
        Map<String, Object> humidityStats = new HashMap<>();
        humidityStats.put("average", String.format("%.2f%%", avgHumidity));
        stats.put("humidity", humidityStats);
        
        long rainCount = allData.stream().filter(d -> d.getRain() != null && d.getRain() == 1).count();
        long snowCount = allData.stream().filter(d -> d.getSnow() != null && d.getSnow() == 1).count();
        long thunderCount = allData.stream().filter(d -> d.getThunder() != null && d.getThunder() == 1).count();
        long fogCount = allData.stream().filter(d -> d.getFog() != null && d.getFog() == 1).count();
        
        Map<String, Object> weatherEvents = new HashMap<>();
        weatherEvents.put("rain", rainCount);
        weatherEvents.put("snow", snowCount);
        weatherEvents.put("thunder", thunderCount);
        weatherEvents.put("fog", fogCount);
        stats.put("weatherEvents", weatherEvents);
        
        return ResponseEntity.ok(stats);
    }

    /**
     * Get available conditions
     * GET /api/weather/conditions
     */
    @GetMapping("/conditions")
    public ResponseEntity<Map<String, Object>> getAvailableConditions() {
        Map<String, Long> conditionCounts = new HashMap<>();
        
        weatherDataRepository.findAll().stream()
                .filter(d -> d.getConditions() != null)
                .forEach(d -> {
                    String condition = d.getConditions();
                    conditionCounts.put(condition, conditionCounts.getOrDefault(condition, 0L) + 1);
                });
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalConditionsFound", conditionCounts.size());
        response.put("conditions", conditionCounts);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get API help/documentation
     * GET /api/weather/help
     */
    @GetMapping("/help")
    public ResponseEntity<Map<String, Object>> getApiDocumentation() {
        Map<String, Object> help = new HashMap<>();
        help.put("baseUrl", "/api/weather");
        
        Map<String, Object> searchEndpoint = new HashMap<>();
        searchEndpoint.put("path", "GET /search");
        searchEndpoint.put("description", "Advanced search with multiple filters and sorting");
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("minTemp/maxTemp", "Filter by temperature range");
        parameters.put("minHumidity/maxHumidity", "Filter by humidity range");
        parameters.put("startDate/endDate", "Filter by date range (format: yyyy-MM-dd HH:mm)");
        parameters.put("condition", "Filter by weather condition");
        parameters.put("minPressure/maxPressure", "Filter by pressure range");
        parameters.put("minVisibility/maxVisibility", "Filter by visibility range");
        parameters.put("minWindSpeed/maxWindSpeed", "Filter by wind speed range");
        parameters.put("weatherEvent", "Filter by event (rain|snow|thunder|fog|hail)");
        parameters.put("sortBy", "Sort field (temperature, humidity, datetime, pressure, visibility, windSpeed)");
        parameters.put("sortDir", "Sort direction (ASC|DESC)");
        parameters.put("page", "Page number (default: 0)");
        parameters.put("size", "Page size (default: 100)");
        searchEndpoint.put("parameters", parameters);
        
        searchEndpoint.put("example", "/search?minTemp=20&maxTemp=30&sortBy=temperature&sortDir=DESC&page=0&size=50");
        help.put("search", searchEndpoint);
        
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("GET /all", "Get all weather data with sorting");
        endpoints.put("GET /id/{id}", "Get weather data by ID");
        endpoints.put("GET /temperature", "Get data by temperature range");
        endpoints.put("GET /humidity", "Get data by humidity range");
        endpoints.put("GET /conditions/{condition}", "Get data by weather condition");
        endpoints.put("GET /conditions", "Get all available weather conditions with counts");
        endpoints.put("GET /events/{eventType}", "Get data by weather event (rain|snow|thunder|fog|hail)");
        endpoints.put("GET /statistics", "Get weather statistics");
        endpoints.put("GET /help", "Get API documentation");
        help.put("endpoints", endpoints);
        
        Map<String, Object> sortFields = new HashMap<>();
        List<String> availableFields = new java.util.ArrayList<>();
        availableFields.add("temperature");
        availableFields.add("humidity");
        availableFields.add("datetime");
        availableFields.add("pressure");
        availableFields.add("visibility");
        availableFields.add("windSpeed");
        availableFields.add("dewpoint");
        availableFields.add("windChill");
        sortFields.put("available_fields", availableFields);
        sortFields.put("note", "Use camelCase or snake_case field names");
        help.put("sorting", sortFields);
        
        return ResponseEntity.ok(help);
    }

    /**
     * Helper method to convert sort field names to entity field names
     */
    private String getSortField(String sortBy) {
        if (sortBy == null) {
            return "datetimeUtc";
        }
        String field = sortBy.toLowerCase();
        if ("temperature".equals(field) || "temp".equals(field)) {
            return "tempm";
        } else if ("humidity".equals(field)) {
            return "humidity";
        } else if ("datetime".equals(field) || "date".equals(field)) {
            return "datetimeUtc";
        } else if ("pressure".equals(field) || "press".equals(field)) {
            return "pressurem";
        } else if ("visibility".equals(field) || "vis".equals(field)) {
            return "visibility";
        } else if ("windspeed".equals(field) || "wind".equals(field)) {
            return "wspdm";
        } else if ("dewpoint".equals(field) || "dew".equals(field)) {
            return "dewptm";
        } else if ("windchill".equals(field) || "chill".equals(field)) {
            return "windchillm";
        } else if ("condition".equals(field)) {
            return "conditions";
        } else {
            return "datetimeUtc";
        }
    }
}

