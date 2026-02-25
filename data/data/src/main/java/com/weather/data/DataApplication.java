package com.weather.data;

import com.weather.data.model.WeatherData;
import com.weather.data.repository.WeatherDataRepository;
import com.weather.data.service.CsvProcessorService;
import com.weather.data.service.WeatherDataStatistics;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}

	@Bean
	public CommandLineRunner processWeatherData(CsvProcessorService csvProcessorService, WeatherDataRepository weatherDataRepository) {
		return args -> {
			System.out.println("=== Starting Weather Data CSV Processing ===");
			
			try {
				System.out.println("Reading CSV file...");
				List<WeatherData> weatherDataList = csvProcessorService.readCsvFile();
				System.out.println("Successfully loaded " + weatherDataList.size() + " records from CSV");

				System.out.println("\n=== Saving Data to Database ===");
				csvProcessorService.saveWeatherDataToDatabase(weatherDataList);

				System.out.println("\n=== Database Statistics ===");
				long totalRecordsInDb = weatherDataRepository.count();
				System.out.println("Total records in database: " + totalRecordsInDb);

				System.out.println("\n=== Analyzing Weather Data ===");
				WeatherDataStatistics statistics = csvProcessorService.analyzeWeatherData(weatherDataList);
				System.out.println(statistics);

				System.out.println("\n=== Sample Data (First 5 records) ===");
				weatherDataList.stream().limit(5).forEach(data -> System.out.println(data));

				System.out.println("\n=== Processing Complete ===");
				System.out.println("H2 Console available at: http://localhost:8080/h2-console");

			} catch (Exception e) {
				System.err.println("Error processing CSV file: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

}
