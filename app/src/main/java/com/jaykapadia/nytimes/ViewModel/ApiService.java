package com.jaykapadia.nytimes.ViewModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiService {
    private static Retrofit retrofit;
    static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://api.nytimes.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
