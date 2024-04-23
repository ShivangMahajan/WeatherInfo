package com.example.demo.service;

import com.example.demo.dto.WeatherInfoDTO;
import com.example.demo.entity.LatlongWeatherMapTbl;
import com.example.demo.entity.PincodeLatLongMapTbl;
import com.example.demo.repository.LatLongWeatherMapRepository;
import com.example.demo.repository.PincodeLatLongMapRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {

    Logger logger = LoggerFactory.getLogger(WeatherInfoServiceImpl.class);

    @Autowired
    public WeatherInfoServiceImpl(PincodeLatLongMapRepository pincodeLatLongMapRepository, LatLongWeatherMapRepository latLongWeatherMapRepository) {
        this.pincodeLatLongMapRepository = pincodeLatLongMapRepository;
        this.latLongWeatherMapRepository = latLongWeatherMapRepository;
        restTemplate = new RestTemplate();
    }

    @Value("${geoapify_key}")
    private String geoapifyKey;

    @Value("${geoapify_url}")
    private String geoapifyUrl;

    @Value("${visualcrossing_url}")
    private String visualCrossingUrl;

    @Value("${visualcrossing_key}")
    private String visualCrossingKey;

    private PincodeLatLongMapRepository pincodeLatLongMapRepository;
    private LatLongWeatherMapRepository latLongWeatherMapRepository;

    private RestTemplate restTemplate;

    @Override
    public WeatherInfoDTO getWeatherInfo(String pinCode, String forDate) {
        logger.info("getWeatherInfo started");
        WeatherInfoDTO res = new WeatherInfoDTO();
        try {
            BigDecimal[] latLong = new BigDecimal[2];
            PincodeLatLongMapTbl pincodeLatLongMapTbl = pincodeLatLongMapRepository.findByPincode(pinCode);
            if (pincodeLatLongMapTbl != null) {
                logger.info("found in db");
                latLong[0] = BigDecimal.valueOf(pincodeLatLongMapTbl.getLatitude());
                latLong[1] = BigDecimal.valueOf(pincodeLatLongMapTbl.getLongitude());
            } else {
                logger.info("calling api to get lat long");
                latLong = convertPinToLatLong(pinCode);
                if (latLong[0] != null && latLong[1] != null) {
                    pincodeLatLongMapTbl = new PincodeLatLongMapTbl();
                    pincodeLatLongMapTbl.setPincode(pinCode);
                    pincodeLatLongMapTbl.setLatitude(latLong[0].doubleValue());
                    pincodeLatLongMapTbl.setLongitude(latLong[1].doubleValue());

                    logger.info("saving in pincodeLatlongMap");
                    pincodeLatLongMapTbl = pincodeLatLongMapRepository.saveAndFlush(pincodeLatLongMapTbl);
                }
            }
            if (pincodeLatLongMapTbl != null && pincodeLatLongMapTbl.getPincodeLatlongMapId() != null) {
                Optional<LatlongWeatherMapTbl> oLatlongWeatherMapTbl = Optional.empty();

                if (pincodeLatLongMapTbl.getLatlongWeatherMapTblList() != null && !pincodeLatLongMapTbl.getLatlongWeatherMapTblList().isEmpty()) {
                    oLatlongWeatherMapTbl = pincodeLatLongMapTbl.getLatlongWeatherMapTblList().stream()
                            .filter(s -> s.getForDate().equalsIgnoreCase(forDate)).findFirst();
                }

                if (oLatlongWeatherMapTbl.isPresent()) {
                    LatlongWeatherMapTbl latlongWeatherMapTbl = oLatlongWeatherMapTbl.get();
                    logger.info("found weather info in table");

                    res.setMaxTemp(BigDecimal.valueOf(latlongWeatherMapTbl.getTempMax()));
                    res.setMinTemp(BigDecimal.valueOf(latlongWeatherMapTbl.getTempMin()));
                    res.setCurrTemp(BigDecimal.valueOf(latlongWeatherMapTbl.getTempCurr()));
                    res.setHumidity(BigDecimal.valueOf(latlongWeatherMapTbl.getHumidity()));
                    res.setWindSpeed(BigDecimal.valueOf(latlongWeatherMapTbl.getWindSpeed()));
                    res.setCondition(latlongWeatherMapTbl.getCondition());
                    res.setDescription(latlongWeatherMapTbl.getDescription());

                } else {
                    logger.info("weather info not found in table, calling weather api");
                    res = callWeatherApi(latLong, forDate);
                    if (res.getMaxTemp() != null) {
                        LatlongWeatherMapTbl latlongWeatherMapTbl = new LatlongWeatherMapTbl();
                        latlongWeatherMapTbl.setForDate(forDate);
                        latlongWeatherMapTbl.setPincodeLatLongMapTbl(pincodeLatLongMapTbl);
                        latlongWeatherMapTbl.setTempMax(res.getMaxTemp().doubleValue());
                        latlongWeatherMapTbl.setTempMin(res.getMinTemp().doubleValue());
                        latlongWeatherMapTbl.setTempCurr(res.getCurrTemp().doubleValue());
                        latlongWeatherMapTbl.setHumidity(res.getHumidity().doubleValue());
                        latlongWeatherMapTbl.setWindSpeed(res.getWindSpeed().doubleValue());
                        latlongWeatherMapTbl.setCondition(res.getCondition());
                        latlongWeatherMapTbl.setDescription(res.getDescription());

                        latLongWeatherMapRepository.saveAndFlush(latlongWeatherMapTbl);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("error in getWeatherInfo " + e);
            e.printStackTrace();
        }
        return res;
    }

    public BigDecimal[] convertPinToLatLong(String pinCode) {
        logger.info("convertPinToLatLong started");
        BigDecimal[] res = new BigDecimal[2];
        URI uri = UriComponentsBuilder.fromUriString(geoapifyUrl)
                .queryParam("text", pinCode)
                .queryParam("apiKey", geoapifyKey)
                .build().toUri();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            logger.info("got ok response from geoapify");
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            if (jsonObject.has("features") && !jsonObject.getJSONArray("features").isEmpty()) {
                JSONObject feature = jsonObject.getJSONArray("features").getJSONObject(0);
                if (feature.has("properties") && feature.getJSONObject("properties").has("lon")) {
                    JSONObject property = feature.getJSONObject("properties");
                    String lat = property.get("lat").toString();
                    String lon = property.get("lon").toString();
                    if (lat != null) {
                        res[0] = new BigDecimal(lat);
                    }
                    if (lon != null) {
                        res[1] = new BigDecimal(lon);
                    }
                }
            }
        }
        return res;
    }

    public WeatherInfoDTO callWeatherApi(BigDecimal[] latLong, String date) throws JsonProcessingException {
        logger.info("callWeatherApi started");

        WeatherInfoDTO weatherInfoDTO = new WeatherInfoDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = visualCrossingUrl +
                latLong[0] +
                "," +
                latLong[1] +
                "/" +
                date;

        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", visualCrossingKey)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "days")
                .build().toUri();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            if (jsonObject.has("days") && !jsonObject.getJSONArray("days").isEmpty()) {
                JSONObject weatherData = jsonObject.getJSONArray("days").getJSONObject(0);
                weatherInfoDTO = objectMapper.readValue(weatherData.toString(), WeatherInfoDTO.class);
            }
        }

        return weatherInfoDTO;
    }

}
