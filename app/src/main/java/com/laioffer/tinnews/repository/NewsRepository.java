package com.laioffer.tinnews.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;

    public NewsRepository(Context context) {
        newsApi = RetrofitClient.newInstance(context).create(NewsApi.class);
        // 为什么newInstance里是context而不是this？因为NewRepository不是context
        // context是用来get系统资源的
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        // LiveData是一个data stream，是一个data holder，可以给别的lifecycle来observe。
        // 相当于一个数据生产线，
        // prevent内存泄漏。
        // 为什么会内存泄漏？
        // 可能一个已经destroy了，但是call里的东西没清空
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                            // 这个比return topHeadlinesLiveData后执行
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }


}
