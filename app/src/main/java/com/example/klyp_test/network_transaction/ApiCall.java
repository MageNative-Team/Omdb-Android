package com.example.klyp_test.network_transaction;

import android.content.Context;
import android.util.Log;

import com.example.klyp_test.R;
import com.example.klyp_test.model.Response;
import com.example.klyp_test.repository.Repository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.example.klyp_test.constants.AppConstant.ConnectivityCheck;

//import static com.example.klyp_test.constants.AppConstant.ConnectivityCheck;


public class ApiCall {
    private final Repository repository;
    private static final String TAG = "ApiCall";

    @Inject
    public ApiCall(Repository repository) {
        this.repository = repository;
    }

    public void postRequest(ApiResponse apiResponse, Single<Response> objectSingle, Context context, CompositeDisposable compositeDisposable) {
        if (ConnectivityCheck(context)) {
            compositeDisposable.add(objectSingle
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Object>() {
                        @Override
                        public void onSuccess(Object value) {
                            try {
                                String response = new Gson().toJson(value);
                                apiResponse.onResponse(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            apiResponse.onError(e.getMessage());
                        }
                    }));
        } else {
            Log.i(TAG, "postRequest: internetGone");
            apiResponse.onError(context.getString(R.string.internet_connection_message));
        }
    }
}
