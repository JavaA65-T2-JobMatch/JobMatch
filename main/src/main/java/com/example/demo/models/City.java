package com.example.demo.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "city_name", length = 30, nullable = false)
    private String cityName;

    public City() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o ){
        if (this == o) return true;
        if (o == null) return false;
        City city = (City) o;
        return cityName.equals(city.cityName);
    }

    @Override
    public int hashCode(){
        return Objects.hash(cityName);
    }


}
