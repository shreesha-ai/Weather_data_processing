package com.weather.data.repository;

import com.weather.data.entity.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long>, JpaSpecificationExecutor<WeatherDataEntity> {
}
