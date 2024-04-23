package com.example.demo.entity;

import jakarta.annotation.Priority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.generator.Generator;
import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;

import java.util.List;

@Entity
@Table(name = "PINCODE_LATLONG_MAP")
@Getter
@Setter
@NoArgsConstructor
public class PincodeLatLongMapTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PINCODE_LATLONG_MAP_ID")
    private Integer pincodeLatlongMapId;

    @Column(name = "PINCODE")
    private String pincode;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @OneToMany(mappedBy = "pincodeLatLongMapTbl", cascade = CascadeType.ALL)
    List<LatlongWeatherMapTbl> latlongWeatherMapTblList;
}
