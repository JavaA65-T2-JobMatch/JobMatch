package com.example.demo.repositories.interfaces;

import com.example.demo.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findCityByCityId(int locationId);


    City getCityByCityId(int cityId);
}
