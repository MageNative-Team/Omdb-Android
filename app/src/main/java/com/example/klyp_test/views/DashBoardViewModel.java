package com.example.klyp_test.views;


import android.content.Context;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.klyp_test.model.Response;
import com.example.klyp_test.network_transaction.ApiCall;
import com.example.klyp_test.network_transaction.ApiResponse;
import com.example.klyp_test.repository.Repository;
import com.google.gson.Gson;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class DashBoardViewModel extends ViewModel {
    private final Repository repository;
    private final ApiCall apiCall;
    private final MutableLiveData<Response> moviesData = new MutableLiveData<>();
    MutableLiveData<String> errorData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    @ViewModelInject
    public DashBoardViewModel(Repository repository, ApiCall apiCall) {
        this.repository = repository;
        this.apiCall = apiCall;
    }

    public MutableLiveData<Response> getMoviesResult(Context context, String keyword) {
        Single<Response> objectSingle = repository.getMoviesData(keyword);
        apiCall.postRequest(response, objectSingle, context, disposable);
        return moviesData;
    }

    private final ApiResponse response = new ApiResponse() {
        @Override
        public void onResponse(String response) {
            Gson gsonObj = new Gson();
            Response res = gsonObj.fromJson(response, Response.class);
            moviesData.setValue(res);
        }

        @Override
        public void onError(String error) {
            errorData.setValue(error);
        }
    };
}
