package com.example.demo.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryTrainStationName {

    @GET("/index/script/core/common/station_name_v10178.js")
    Call<String> getStationName();
}
