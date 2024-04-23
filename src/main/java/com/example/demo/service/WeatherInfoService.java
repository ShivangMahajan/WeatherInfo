package com.example.demo.service;

import com.example.demo.dto.WeatherInfoDTO;

public interface WeatherInfoService {
    public WeatherInfoDTO getWeatherInfo(String pinCode, String forDate);
}
