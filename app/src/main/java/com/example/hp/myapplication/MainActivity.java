package com.example.hp.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    GetDataService getDataService;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<Items> list = new ArrayList<Items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataService =ApiClient.getClient().create(GetDataService.class);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Call<Coin> call= getDataService.getCoins();

        call.enqueue(new Callback<Coin>() {
            @Override
            public void onResponse(Call<Coin> call, Response<Coin> response) {
                Coin coinList=response.body();
                for (int i=0;i<coinList.getData().getCoins().size();i++) {
                    Items items = new Items();
                    items.setId(coinList.getData().getCoins().get(i).getId());
                    items.setSymbol(coinList.getData().getCoins().get(i).getSymbol());
                    items.setName(coinList.getData().getCoins().get(i).getName());
                    items.setDescription(coinList.getData().getCoins().get(i).getDescription());
                    items.setIconUrl(coinList.getData().getCoins().get(i).getIconUrl());
                    items.setPrice(coinList.getData().getCoins().get(i).getPrice());
                    items.setColor(coinList.getData().getCoins().get(i).getColor());
                    list.add(items);
                }
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new RecyclerViewCustomAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<Coin> call, Throwable t) {

            }
        });

    }
}
