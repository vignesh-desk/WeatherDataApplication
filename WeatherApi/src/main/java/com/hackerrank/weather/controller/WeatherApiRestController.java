package com.hackerrank.weather.controller;

import com.hackerrank.weather.exception.DuplicateWeatherDataException;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class WeatherApiRestController {

    private WeatherService weatherService;

    @Autowired
    public WeatherApiRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Weather createWeather(@Valid @RequestBody Weather weatherData) throws DuplicateWeatherDataException {
        return weatherService.create(weatherData);
    }

    @DeleteMapping(value = "/erase")
    @ResponseStatus(HttpStatus.OK)
    public void eraseAllWeatherInformation() {
        weatherService.eraseAllWeatherData();
    }

    @DeleteMapping(value="/erase", params = {"start", "end", "lat", "lon"})
    @ResponseStatus(HttpStatus.OK)
    public void eraseSpecificWeatherData( @RequestParam("start") String startDate,
                                          @RequestParam("end") String endDate,
                                          @RequestParam("lat") float latitude,
                                          @RequestParam("lon") float longitude
                                         ) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        weatherService.eraseWeatherDataForGivenDateRangeAndLocation( simpleDateFormat.parse(startDate),
                simpleDateFormat.parse(endDate), latitude, longitude);
    }


}
