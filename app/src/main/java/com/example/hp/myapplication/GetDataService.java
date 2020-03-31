package com.example.hp.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("v1/public/coins/")
    Call<Coin> getCoins();
}