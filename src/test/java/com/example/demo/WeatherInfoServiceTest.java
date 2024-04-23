package com.example.demo;

import com.example.demo.entity.PincodeLatLongMapTbl;
import com.example.demo.repository.LatLongWeatherMapRepository;
import com.example.demo.repository.PincodeLatLongMapRepository;
import com.example.demo.service.WeatherInfoService;
import com.example.demo.service.WeatherInfoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherInfoServiceTest {

    @Mock
    LatLongWeatherMapRepository latLongWeatherMapRepository;

    @Mock
    PincodeLatLongMapRepository pincodeLatLongMapRepository;

    WeatherInfoService weatherInfoService;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setup() {
        weatherInfoService = new WeatherInfoServiceImpl(pincodeLatLongMapRepository, latLongWeatherMapRepository);


        ReflectionTestUtils.setField(weatherInfoService, "geoapifyKey", "123");
        ReflectionTestUtils.setField(weatherInfoService, "geoapifyUrl", "http://www.123.com");
        ReflectionTestUtils.setField(weatherInfoService, "visualCrossingUrl", "http://www.123.com/");
        ReflectionTestUtils.setField(weatherInfoService, "visualCrossingKey", "123");
        ReflectionTestUtils.setField(weatherInfoService, "restTemplate", restTemplate);
    }

    @Test
    public void getWeatherInfo() {
        String latLong = "{\n" +
                "    \"type\": \"FeatureCollection\",\n" +
                "    \"features\": [\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"datasource\": {\n" +
                "                    \"sourcename\": \"openstreetmap\",\n" +
                "                    \"attribution\": \"© OpenStreetMap contributors\",\n" +
                "                    \"license\": \"Open Database License\",\n" +
                "                    \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
                "                },\n" +
                "                \"name\": \"Jammu\",\n" +
                "                \"country\": \"India\",\n" +
                "                \"country_code\": \"in\",\n" +
                "                \"state\": \"Jammu and Kashmir\",\n" +
                "                \"state_district\": \"Jammu District\",\n" +
                "                \"county\": \"Jammu\",\n" +
                "                \"postcode\": \"180001\",\n" +
                "                \"lon\": 74.86250688809524,\n" +
                "                \"lat\": 32.72517342857142,\n" +
                "                \"state_code\": \"JK\",\n" +
                "                \"result_type\": \"postcode\",\n" +
                "                \"formatted\": \"Jammu, 180001, JK, India\",\n" +
                "                \"address_line1\": \"Jammu\",\n" +
                "                \"address_line2\": \"180001, JK, India\",\n" +
                "                \"timezone\": {\n" +
                "                    \"name\": \"Asia/Kolkata\",\n" +
                "                    \"offset_STD\": \"+05:30\",\n" +
                "                    \"offset_STD_seconds\": 19800,\n" +
                "                    \"offset_DST\": \"+05:30\",\n" +
                "                    \"offset_DST_seconds\": 19800,\n" +
                "                    \"abbreviation_STD\": \"IST\",\n" +
                "                    \"abbreviation_DST\": \"IST\"\n" +
                "                },\n" +
                "                \"plus_code\": \"8J4PPVG7+32\",\n" +
                "                \"rank\": {\n" +
                "                    \"importance\": 0.33499999999999996,\n" +
                "                    \"popularity\": 0.8561856660285178,\n" +
                "                    \"confidence\": 1,\n" +
                "                    \"confidence_city_level\": 1,\n" +
                "                    \"match_type\": \"full_match\"\n" +
                "                },\n" +
                "                \"place_id\": \"51673c175033b75240593cd29f7bd25c4040c002079203093138303030312b696e\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    74.86250688809524,\n" +
                "                    32.72517342857142\n" +
                "                ]\n" +
                "            },\n" +
                "            \"bbox\": [\n" +
                "                74.702506888095,\n" +
                "                32.565173428571,\n" +
                "                75.022506888095,\n" +
                "                32.885173428571\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"datasource\": {\n" +
                "                    \"sourcename\": \"openstreetmap\",\n" +
                "                    \"attribution\": \"© OpenStreetMap contributors\",\n" +
                "                    \"license\": \"Open Database License\",\n" +
                "                    \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
                "                },\n" +
                "                \"name\": \"Florencia\",\n" +
                "                \"country\": \"Colombia\",\n" +
                "                \"country_code\": \"co\",\n" +
                "                \"region\": \"RAP Amazonía\",\n" +
                "                \"state\": \"Caquetá\",\n" +
                "                \"county\": \"Florencia\",\n" +
                "                \"postcode\": \"180001\",\n" +
                "                \"lon\": -75.61474143333334,\n" +
                "                \"lat\": 1.5928841333333335,\n" +
                "                \"state_code\": \"CAQ\",\n" +
                "                \"result_type\": \"postcode\",\n" +
                "                \"formatted\": \"Florencia, 180001, CAQ, Colombia\",\n" +
                "                \"address_line1\": \"Florencia\",\n" +
                "                \"address_line2\": \"180001, CAQ, Colombia\",\n" +
                "                \"timezone\": {\n" +
                "                    \"name\": \"America/Bogota\",\n" +
                "                    \"offset_STD\": \"-05:00\",\n" +
                "                    \"offset_STD_seconds\": -18000,\n" +
                "                    \"offset_DST\": \"-05:00\",\n" +
                "                    \"offset_DST_seconds\": -18000\n" +
                "                },\n" +
                "                \"plus_code\": \"67H6H9VP+54\",\n" +
                "                \"rank\": {\n" +
                "                    \"importance\": 0.33499999999999996,\n" +
                "                    \"popularity\": 1.6191372358540033,\n" +
                "                    \"confidence\": 1,\n" +
                "                    \"confidence_city_level\": 1,\n" +
                "                    \"match_type\": \"full_match\"\n" +
                "                },\n" +
                "                \"place_id\": \"516cea73ec57e752c05958beaf12747cf93fc002079203093138303030312b636f\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    -75.61474143333334,\n" +
                "                    1.5928841333333335\n" +
                "                ]\n" +
                "            },\n" +
                "            \"bbox\": [\n" +
                "                -75.774741433333,\n" +
                "                1.4328841333333,\n" +
                "                -75.454741433333,\n" +
                "                1.7528841333333\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"datasource\": {\n" +
                "                    \"sourcename\": \"openstreetmap\",\n" +
                "                    \"attribution\": \"© OpenStreetMap contributors\",\n" +
                "                    \"license\": \"Open Database License\",\n" +
                "                    \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
                "                },\n" +
                "                \"name\": \"180001\",\n" +
                "                \"ref\": \"180001\",\n" +
                "                \"country\": \"Spain\",\n" +
                "                \"country_code\": \"es\",\n" +
                "                \"state\": \"Navarre\",\n" +
                "                \"state_district\": \"Navarre\",\n" +
                "                \"county\": \"Baztan-Bidasoa\",\n" +
                "                \"city\": \"Baztan\",\n" +
                "                \"village\": \"Erratzu\",\n" +
                "                \"postcode\": \"31714\",\n" +
                "                \"street\": \"Utselei karrika\",\n" +
                "                \"lon\": -1.4590157,\n" +
                "                \"lat\": 43.1827835,\n" +
                "                \"result_type\": \"amenity\",\n" +
                "                \"formatted\": \"180001, Utselei karrika, 31714 Baztan, Spain\",\n" +
                "                \"address_line1\": \"180001\",\n" +
                "                \"address_line2\": \"Utselei karrika, 31714 Baztan, Spain\",\n" +
                "                \"timezone\": {\n" +
                "                    \"name\": \"Europe/Madrid\",\n" +
                "                    \"offset_STD\": \"+01:00\",\n" +
                "                    \"offset_STD_seconds\": 3600,\n" +
                "                    \"offset_DST\": \"+02:00\",\n" +
                "                    \"offset_DST_seconds\": 7200,\n" +
                "                    \"abbreviation_STD\": \"CET\",\n" +
                "                    \"abbreviation_DST\": \"CEST\"\n" +
                "                },\n" +
                "                \"plus_code\": \"8CMW5GMR+49\",\n" +
                "                \"rank\": {\n" +
                "                    \"importance\": 0.11000999999999997,\n" +
                "                    \"popularity\": 4.356248559077806,\n" +
                "                    \"confidence\": 1,\n" +
                "                    \"match_type\": \"full_match\"\n" +
                "                },\n" +
                "                \"place_id\": \"51d79bbdd82058f7bf59cc5f217365974540f00103f90167df199702000000c00201920306313830303031\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    -1.4590157,\n" +
                "                    43.1827835\n" +
                "                ]\n" +
                "            },\n" +
                "            \"bbox\": [\n" +
                "                -1.4590657,\n" +
                "                43.1827335,\n" +
                "                -1.4589657,\n" +
                "                43.1828335\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"datasource\": {\n" +
                "                    \"sourcename\": \"openstreetmap\",\n" +
                "                    \"attribution\": \"© OpenStreetMap contributors\",\n" +
                "                    \"license\": \"Open Database License\",\n" +
                "                    \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
                "                },\n" +
                "                \"name\": \"15th Ave at Jarvis (EB)\",\n" +
                "                \"ref\": \"180001\",\n" +
                "                \"country\": \"Canada\",\n" +
                "                \"country_code\": \"ca\",\n" +
                "                \"state\": \"British Columbia\",\n" +
                "                \"county\": \"Regional District of Fraser-Fort George\",\n" +
                "                \"city\": \"Prince George\",\n" +
                "                \"postcode\": \"V2M 1V8\",\n" +
                "                \"street\": \"15th Avenue\",\n" +
                "                \"lon\": -122.803736,\n" +
                "                \"lat\": 53.910153,\n" +
                "                \"state_code\": \"BC\",\n" +
                "                \"result_type\": \"amenity\",\n" +
                "                \"formatted\": \"15th Ave at Jarvis (EB), 15th Avenue, Prince George, BC V2M 1V8, Canada\",\n" +
                "                \"address_line1\": \"15th Ave at Jarvis (EB)\",\n" +
                "                \"address_line2\": \"15th Avenue, Prince George, BC V2M 1V8, Canada\",\n" +
                "                \"category\": \"public_transport.bus\",\n" +
                "                \"timezone\": {\n" +
                "                    \"name\": \"America/Vancouver\",\n" +
                "                    \"offset_STD\": \"-08:00\",\n" +
                "                    \"offset_STD_seconds\": -28800,\n" +
                "                    \"offset_DST\": \"-07:00\",\n" +
                "                    \"offset_DST_seconds\": -25200,\n" +
                "                    \"abbreviation_STD\": \"PST\",\n" +
                "                    \"abbreviation_DST\": \"PDT\"\n" +
                "                },\n" +
                "                \"plus_code\": \"945VW56W+3G\",\n" +
                "                \"plus_code_short\": \"W56W+3G Prince George, Regional District of Fraser-Fort George, Canada\",\n" +
                "                \"rank\": {\n" +
                "                    \"importance\": 0.00000999999999995449,\n" +
                "                    \"popularity\": 0.7686896328671642,\n" +
                "                    \"confidence\": 1,\n" +
                "                    \"match_type\": \"full_match\"\n" +
                "                },\n" +
                "                \"place_id\": \"518ba71e6970b35ec0599badbce47ff44a40f00103f90104a077ff01000000c002019203173135746820417665206174204a61727669732028454229\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    -122.803736,\n" +
                "                    53.910153\n" +
                "                ]\n" +
                "            },\n" +
                "            \"bbox\": [\n" +
                "                -122.803786,\n" +
                "                53.910103,\n" +
                "                -122.803686,\n" +
                "                53.910203\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"Feature\",\n" +
                "            \"properties\": {\n" +
                "                \"datasource\": {\n" +
                "                    \"sourcename\": \"openstreetmap\",\n" +
                "                    \"attribution\": \"© OpenStreetMap contributors\",\n" +
                "                    \"license\": \"Open Database License\",\n" +
                "                    \"url\": \"https://www.openstreetmap.org/copyright\"\n" +
                "                },\n" +
                "                \"name\": \"PKP Legionowo 01\",\n" +
                "                \"ref\": \"180001\",\n" +
                "                \"country\": \"Poland\",\n" +
                "                \"country_code\": \"pl\",\n" +
                "                \"state\": \"Masovian Voivodeship\",\n" +
                "                \"county\": \"Legionowo County\",\n" +
                "                \"city\": \"Legionowo\",\n" +
                "                \"postcode\": \"05-122\",\n" +
                "                \"district\": \"Osiedle Batorego\",\n" +
                "                \"street\": \"Tadeusza Kościuszki\",\n" +
                "                \"lon\": 20.9414895,\n" +
                "                \"lat\": 52.4007349,\n" +
                "                \"result_type\": \"amenity\",\n" +
                "                \"formatted\": \"PKP Legionowo 01, Tadeusza Kościuszki, 05-122 Legionowo, Poland\",\n" +
                "                \"address_line1\": \"PKP Legionowo 01\",\n" +
                "                \"address_line2\": \"Tadeusza Kościuszki, 05-122 Legionowo, Poland\",\n" +
                "                \"category\": \"public_transport.bus\",\n" +
                "                \"timezone\": {\n" +
                "                    \"name\": \"Europe/Warsaw\",\n" +
                "                    \"offset_STD\": \"+01:00\",\n" +
                "                    \"offset_STD_seconds\": 3600,\n" +
                "                    \"offset_DST\": \"+02:00\",\n" +
                "                    \"offset_DST_seconds\": 7200,\n" +
                "                    \"abbreviation_STD\": \"CET\",\n" +
                "                    \"abbreviation_DST\": \"CEST\"\n" +
                "                },\n" +
                "                \"plus_code\": \"9G42CW2R+7H\",\n" +
                "                \"plus_code_short\": \"2R+7H Legionowo, Legionowo County, Poland\",\n" +
                "                \"rank\": {\n" +
                "                    \"importance\": 0.00000999999999995449,\n" +
                "                    \"popularity\": 7.4368126361846425,\n" +
                "                    \"confidence\": 1,\n" +
                "                    \"match_type\": \"full_match\"\n" +
                "                },\n" +
                "                \"place_id\": \"510307b47405f1344059d4eefc474b334a40f00103f9019e8f987000000000c00201920310504b50204c6567696f6e6f776f203031\"\n" +
                "            },\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [\n" +
                "                    20.9414895,\n" +
                "                    52.4007349\n" +
                "                ]\n" +
                "            },\n" +
                "            \"bbox\": [\n" +
                "                20.9414395,\n" +
                "                52.4006849,\n" +
                "                20.9415395,\n" +
                "                52.4007849\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"query\": {\n" +
                "        \"text\": \"180001\",\n" +
                "        \"parsed\": {\n" +
                "            \"postcode\": \"180001\",\n" +
                "            \"expected_type\": \"unknown\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ResponseEntity<String> responseEntity = ResponseEntity.of(Optional.of(latLong));
        URI uri = URI.create("http://www.123.com?text=411014&apiKey=123");

        String weather = "{\n" +
                "    \"queryCost\": 1,\n" +
                "    \"latitude\": 32.72517342857142,\n" +
                "    \"longitude\": 74.86250688809524,\n" +
                "    \"resolvedAddress\": \"32.72517342857142,74.86250688809524\",\n" +
                "    \"address\": \"32.72517342857142,74.86250688809524\",\n" +
                "    \"timezone\": \"Asia/Kolkata\",\n" +
                "    \"tzoffset\": 5.5,\n" +
                "    \"days\": [\n" +
                "        {\n" +
                "            \"datetime\": \"2020-10-15\",\n" +
                "            \"datetimeEpoch\": 1602700200,\n" +
                "            \"tempmax\": 33.0,\n" +
                "            \"tempmin\": 17.2,\n" +
                "            \"temp\": 24.7,\n" +
                "            \"feelslikemax\": 31.6,\n" +
                "            \"feelslikemin\": 17.2,\n" +
                "            \"feelslike\": 24.4,\n" +
                "            \"dew\": 14.4,\n" +
                "            \"humidity\": 57.2,\n" +
                "            \"precip\": 0.0,\n" +
                "            \"precipprob\": 0.0,\n" +
                "            \"precipcover\": 0.0,\n" +
                "            \"preciptype\": null,\n" +
                "            \"snow\": 0.0,\n" +
                "            \"snowdepth\": 0.0,\n" +
                "            \"windgust\": 18.4,\n" +
                "            \"windspeed\": 11.2,\n" +
                "            \"winddir\": 329.8,\n" +
                "            \"pressure\": 1008.1,\n" +
                "            \"cloudcover\": 0.2,\n" +
                "            \"visibility\": 4.2,\n" +
                "            \"solarradiation\": 215.9,\n" +
                "            \"solarenergy\": 18.6,\n" +
                "            \"uvindex\": 8.0,\n" +
                "            \"sunrise\": \"06:34:37\",\n" +
                "            \"sunriseEpoch\": 1602723877,\n" +
                "            \"sunset\": \"17:57:28\",\n" +
                "            \"sunsetEpoch\": 1602764848,\n" +
                "            \"moonphase\": 0.95,\n" +
                "            \"conditions\": \"Clear\",\n" +
                "            \"description\": \"Clear conditions throughout the day.\",\n" +
                "            \"icon\": \"clear-day\",\n" +
                "            \"stations\": [\n" +
                "                \"OPST\",\n" +
                "                \"42056099999\",\n" +
                "                \"41600099999\"\n" +
                "            ],\n" +
                "            \"source\": \"obs\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"stations\": {\n" +
                "        \"42056099999\": {\n" +
                "            \"distance\": 7061.0,\n" +
                "            \"latitude\": 32.667,\n" +
                "            \"longitude\": 74.833,\n" +
                "            \"useCount\": 0,\n" +
                "            \"id\": \"42056099999\",\n" +
                "            \"name\": \"JAMMU, IN\",\n" +
                "            \"quality\": 66,\n" +
                "            \"contribution\": 0.0\n" +
                "        },\n" +
                "        \"41600099999\": {\n" +
                "            \"distance\": 39764.0,\n" +
                "            \"latitude\": 32.5,\n" +
                "            \"longitude\": 74.533,\n" +
                "            \"useCount\": 0,\n" +
                "            \"id\": \"41600099999\",\n" +
                "            \"name\": \"SIALKOT, PK\",\n" +
                "            \"quality\": 74,\n" +
                "            \"contribution\": 0.0\n" +
                "        },\n" +
                "        \"OPST\": {\n" +
                "            \"distance\": 51030.0,\n" +
                "            \"latitude\": 32.53,\n" +
                "            \"longitude\": 74.37,\n" +
                "            \"useCount\": 0,\n" +
                "            \"id\": \"OPST\",\n" +
                "            \"name\": \"OPST\",\n" +
                "            \"quality\": 49,\n" +
                "            \"contribution\": 0.0\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ResponseEntity<String> weatherEntity = ResponseEntity.of(Optional.of(weather));
        URI uri2 = URI.create("http://www.123.com/32.72517342857142,74.86250688809524/2020-10-15?key=123&unitGroup=metric&include=days");
        when(restTemplate.getForEntity(uri, String.class)).thenReturn(responseEntity);
        when(restTemplate.getForEntity(uri2, String.class)).thenReturn(weatherEntity);
        PincodeLatLongMapTbl pincodeLatLongMapTbl = new PincodeLatLongMapTbl();
        pincodeLatLongMapTbl.setPincodeLatlongMapId(1);
        doReturn(pincodeLatLongMapTbl).when(pincodeLatLongMapRepository).saveAndFlush(any());

        Assert.assertEquals(weatherInfoService.getWeatherInfo("411014", "2020-10-15").getCondition(), "Clear");
    }

}
