package com.example.demo.repository;

import com.example.demo.entity.LatlongWeatherMapId;
import com.example.demo.entity.LatlongWeatherMapTbl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LatLongWeatherMapRepository extends JpaRepository<LatlongWeatherMapTbl, LatlongWeatherMapId> {
}
