package com.example.demo.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryTrainStationName {

    //@GET("/index/script/core/common/station_name_v10178.js")
    @GET("/otn/resources/js/framework/station_name.js?station_version=1.9260")
    Call<String> getStationName();
}
