package com.worldsay.pojo;

/**
 * Created by gekson on 2015/8/19.
 */
public class Country {
    private int country_id;
    private String country_name;
    private String country_en_name;
    private String country_image_url;

    private int popularity;
    private int continent;

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getContinent() {
        return continent;
    }

    public void setContinent(int continent) {
        this.continent = continent;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_en_name() {
        return country_en_name;
    }

    public void setCountry_en_name(String country_en_name) {
        this.country_en_name = country_en_name;
    }

    public String getCountry_image_url() {
        return country_image_url;
    }

    public void setCountry_image_url(String country_image_url) {
        this.country_image_url = country_image_url;
    }
}
