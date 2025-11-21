package com.example.a21112025_retrofit2.api;

import com.example.a21112025_retrofit2.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
}
