package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfoDTO {

    @JsonProperty("tempmax")
    private BigDecimal maxTemp;

    @JsonProperty("tempmin")
    private BigDecimal minTemp;

    @JsonProperty("temp")
    private BigDecimal currTemp;

    @JsonProperty("humidity")
    private BigDecimal humidity;

    @JsonProperty("windspeed")
    private BigDecimal windSpeed;

    @JsonProperty("conditions")
    private String condition;

    @JsonProperty("description")
    private String description;
}
