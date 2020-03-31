package com.example.hp.myapplication;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("coins")
    @Expose
    private List<Coins> coins = null;

    public List<Coins> getCoins() {
        return coins;
    }

}