package com.weather.data.service;

import com.weather.data.entity.WeatherDataEntity;
import com.weather.data.model.WeatherData;
import com.weather.data.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvProcessorService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");
    
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public List<WeatherData> readCsvFile() throws Exception {
        List<WeatherData> weatherDataList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("test/testset.csv").getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header line
                }

                WeatherData data = parseWeatherDataLine(line);
                if (data != null) {
                    weatherDataList.add(data);
                }
            }
        }

        return weatherDataList;
    }

    public void saveWeatherDataToDatabase(List<WeatherData> weatherDataList) {
        System.out.println("Saving " + weatherDataList.size() + " records to database...");
        
        List<WeatherDataEntity> entities = new ArrayList<>();
        for (WeatherData data : weatherDataList) {
            WeatherDataEntity entity = new WeatherDataEntity(
                    data.getDatetimeUtc(),
                    data.getConditions(),
                    data.getDewptm(),
                    data.getFog(),
                    data.getHail(),
                    data.getHeatindexm(),
                    data.getHumidity(),
                    data.getPrecipm(),
                    data.getPerssurem(),
                    data.getRain(),
                    data.getSnow(),
                    data.getTempm(),
                    data.getThunder(),
                    data.getTornado(),
                    data.getVisibility(),
                    data.getWindDirection(),
                    data.getWindDirectionName(),
                    data.getWindGustm(),
                    data.getWindchillm(),
                    data.getWspdm()
            );
            entities.add(entity);
        }
        
        // Save all entities using saveAll for batch insert
        weatherDataRepository.saveAll(entities);
        System.out.println("Successfully saved " + entities.size() + " records to database");
    }

    private WeatherData parseWeatherDataLine(String line) {
        try {
            String[] parts = line.split(",", -1);

            if (parts.length < 20) {
                return null;
            }

            WeatherData data = new WeatherData();

            // Parse datetime_utc
            data.setDatetimeUtc(LocalDateTime.parse(parts[0].trim(), dateTimeFormatter));

            // Parse conditions
            data.setConditions(parseString(parts[1]));

            // Parse dewptm
            data.setDewptm(parseDouble(parts[2]));

            // Parse fog
            data.setFog(parseInteger(parts[3]));

            // Parse hail
            data.setHail(parseInteger(parts[4]));

            // Parse heatindexm
            data.setHeatindexm(parseDouble(parts[5]));

            // Parse humidity
            data.setHumidity(parseInteger(parts[6]));

            // Parse precipm
            data.setPrecipm(parseDouble(parts[7]));

            // Parse pressurem - skip -9999 invalid values
            Double pressure = parseDouble(parts[8]);
            if (pressure != null && pressure != -9999) {
                data.setPressurem(pressure);
            }

            // Parse rain
            data.setRain(parseInteger(parts[9]));

            // Parse snow
            data.setSnow(parseInteger(parts[10]));

            // Parse tempm
            data.setTempm(parseDouble(parts[11]));

            // Parse thunder
            data.setThunder(parseInteger(parts[12]));

            // Parse tornado
            data.setTornado(parseInteger(parts[13]));

            // Parse visibility
            data.setVisibility(parseDouble(parts[14]));

            // Parse wind direction degree
            data.setWindDirection(parseInteger(parts[15]));

            // Parse wind direction name
            data.setWindDirectionName(parseString(parts[16]));

            // Parse wind gust
            data.setWindGustm(parseDouble(parts[17]));

            // Parse wind chill
            data.setWindchillm(parseDouble(parts[18]));

            // Parse wind speed
            data.setWspdm(parseDouble(parts[19]));

            return data;
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    private String parseString(String value) {
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private Double parseDouble(String value) {
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public WeatherDataStatistics analyzeWeatherData(List<WeatherData> dataList) {
        WeatherDataStatistics stats = new WeatherDataStatistics();

        stats.setTotalRecords(dataList.size());

        // Calculate temperature statistics
        Double avgTemp = dataList.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherData::getTempm)
                .average()
                .orElse(0.0);
        stats.setAvgTemperature(avgTemp);

        Double maxTemp = dataList.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherData::getTempm)
                .max()
                .orElse(0.0);
        stats.setMaxTemperature(maxTemp);

        Double minTemp = dataList.stream()
                .filter(d -> d.getTempm() != null)
                .mapToDouble(WeatherData::getTempm)
                .min()
                .orElse(0.0);
        stats.setMinTemperature(minTemp);

        // Calculate humidity statistics
        Double avgHumidity = dataList.stream()
                .filter(d -> d.getHumidity() != null)
                .mapToDouble(WeatherData::getHumidity)
                .average()
                .orElse(0.0);
        stats.setAvgHumidity(avgHumidity);

        // Count weather conditions
        long rainCount = dataList.stream().filter(d -> d.getRain() != null && d.getRain() == 1).count();
        stats.setRainCount(rainCount);

        long snowCount = dataList.stream().filter(d -> d.getSnow() != null && d.getSnow() == 1).count();
        stats.setSnowCount(snowCount);

        long thunderCount = dataList.stream().filter(d -> d.getThunder() != null && d.getThunder() == 1).count();
        stats.setThunderCount(thunderCount);

        long fogCount = dataList.stream().filter(d -> d.getFog() != null && d.getFog() == 1).count();
        stats.setFogCount(fogCount);

        return stats;
    }
}
