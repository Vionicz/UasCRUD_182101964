package com.dipangestu.uascrud.services;

import com.dipangestu.uascrud.model.GetUser;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/users")
    Call<GetUser> getUsersList();
}
