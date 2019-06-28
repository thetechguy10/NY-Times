package com.jaykapadia.nytimes.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaykapadia.nytimes.Model.Section;

public class ArticleViewModel extends AndroidViewModel {
    private MutableLiveData<Section> mutableLiveData;
    private ArticleRepository repository;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository=ArticleRepository.getInstance();
        mutableLiveData =repository.getArticle("technology.json","mGJPB8xPlE6yGgXRGadGsVhGBZzS6n5Y");

    }
    public LiveData<Section> getArticleRepository(){
        return mutableLiveData;
    }

    public LiveData<Section> getArticleRepository(String section){
        return repository.getArticle(section,"mGJPB8xPlE6yGgXRGadGsVhGBZzS6n5Y");
    }

}
