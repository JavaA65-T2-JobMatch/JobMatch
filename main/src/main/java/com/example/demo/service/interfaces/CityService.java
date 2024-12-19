package com.example.demo.service.interfaces;

import com.example.demo.models.City;

import java.util.List;

public interface CityService {
    City findCityById(int locationId);

    List<City> getAllCities();

    City getCityByCityId(int cityId);
}
