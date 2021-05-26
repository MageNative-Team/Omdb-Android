package com.example.klyp_test.repository;

import com.example.klyp_test.model.Response;
import com.example.klyp_test.retrofit_service.ApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

import static com.example.klyp_test.constants.AppConstant.APIKEY;
import static com.example.klyp_test.constants.AppConstant.RESULT_TYPE;

public class Repository {
    private final ApiService apiService;

    @Inject
    public Repository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Response> getMoviesData(String keyword) {
        return apiService.getMovies(APIKEY, keyword, RESULT_TYPE);
    }

}
