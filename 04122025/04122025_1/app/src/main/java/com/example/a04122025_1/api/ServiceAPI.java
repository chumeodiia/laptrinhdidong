package com.example.a04122025_1.api;

import android.os.Message;

import com.example.a04122025_1.Const.Const;
import com.example.a04122025_1.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {
    @Multipart
    @POST("updateimages.php")
    Call<ApiResponse> upload(
            @Part(Const.MY_USERNAME) RequestBody username,
            @Part MultipartBody.Part avatar
    );

    @Multipart
    @POST("upload1.php")
    Call<Message> upload1(
            @Part(Const.MY_USERNAME) RequestBody username,
            @Part MultipartBody.Part avatar
    );
}
