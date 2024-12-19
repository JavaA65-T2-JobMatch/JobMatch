package com.example.demo.service;

import com.example.demo.models.City;
import com.example.demo.repositories.interfaces.CityRepository;
import com.example.demo.service.interfaces.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;

    }
    @Override
    public City findCityById(int locationId) {
        return cityRepository.findCityByCityId(locationId);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityByCityId(int cityId) {
        return cityRepository.getCityByCityId(cityId);
    }
}
