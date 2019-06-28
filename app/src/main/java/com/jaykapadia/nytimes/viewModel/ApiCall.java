package com.jaykapadia.nytimes.viewModel;

import com.jaykapadia.nytimes.model.Section;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiCall {

    @GET("/svc/topstories/v2/{section}")
    Call<Section> getSection(@Path("section") String section, @Query("api-key") String key);
}
