package com.example.demo.service;

import com.example.demo.models.City;
import com.example.demo.repositories.interfaces.CityRepository;
import com.example.demo.service.interfaces.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;

    }
    @Override
    public City findCityById(int locationId) {
        return cityRepository.findCityByCityId(locationId);
    }
}
