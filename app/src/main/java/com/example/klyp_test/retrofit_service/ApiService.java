package com.example.klyp_test.retrofit_service;

import com.example.klyp_test.model.Response;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.klyp_test.constants.AppConstant.BASE_URL;

public interface ApiService {
    @GET(BASE_URL)
    Single<Response> getMovies(@Query("apikey") String apikey, @Query("s") String keyword, @Query("type") String type);
}
