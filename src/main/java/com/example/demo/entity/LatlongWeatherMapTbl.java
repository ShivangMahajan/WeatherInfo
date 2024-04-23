package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LATLONG_WEATHER_MAP")
@Getter
@Setter
@NoArgsConstructor
@IdClass(LatlongWeatherMapId.class)
public class LatlongWeatherMapTbl {

    @Id
    @Column(name = "FOR_DATE")
    private String forDate;

    @Id
    @ManyToOne
    @JoinColumn(name = "PINCODE_LATLONG_MAP_ID")
    private PincodeLatLongMapTbl pincodeLatLongMapTbl;

    @Column(name = "TEMP_MAX")
    private Double tempMax;

    @Column(name = "TEMP_MIN")
    private Double tempMin;

    @Column(name = "TEMP_CURR")
    private Double tempCurr;

    @Column(name = "HUMIDITY")
    private Double humidity;

    @Column(name = "WIND_SPEED")
    private Double windSpeed;

    @Column(name = "COND")
    private String condition;

    @Column(name = "DESCRIPTION")
    private String description;

}
