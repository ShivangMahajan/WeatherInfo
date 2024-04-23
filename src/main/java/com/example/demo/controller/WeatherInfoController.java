package com.example.demo.controller;

import com.example.demo.service.WeatherInfoService;
import com.example.demo.dto.WeatherInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weather/")
public class WeatherInfoController {

    @Autowired
    WeatherInfoService weatherInfoService;

    @GetMapping("info")
    public WeatherInfoDTO getWeatherInfo(@RequestParam String pincode, @RequestParam String for_date){
        return weatherInfoService.getWeatherInfo(pincode, for_date);
    }

}
