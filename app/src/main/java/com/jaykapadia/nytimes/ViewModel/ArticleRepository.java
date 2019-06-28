package com.jaykapadia.nytimes.ViewModel;


import androidx.lifecycle.MutableLiveData;
import com.jaykapadia.nytimes.Model.Section;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
class ArticleRepository {

    private static ArticleRepository repository;

    static ArticleRepository getInstance() {
        if (repository == null) {
            repository = new ArticleRepository();
        }
        return repository;
    }

    private ApiCall apiCall;

    private ArticleRepository() {
        apiCall = ApiService.getRetrofit().create(ApiCall.class);
    }


    MutableLiveData<Section> getArticle(String section, String key) {
        final MutableLiveData<Section> articleData = new MutableLiveData<>();
        apiCall.getSection(section, key).enqueue(new Callback<Section>() {
            @Override
            public void onResponse(Call<Section> call, Response<Section> response) {
                if (response.isSuccessful()) {
                    articleData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Section> call, Throwable t) {
                articleData.setValue(null);
            }
        });
        return articleData;
    }
}
