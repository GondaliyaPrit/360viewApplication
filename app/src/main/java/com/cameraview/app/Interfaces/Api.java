package com.cameraview.app.Interfaces;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
//    @Multipart
//    @Headers({
//            "X-Api-Key: bhdnzH6ohhL62EthseRDY8ZF",
//    })
//    @POST("removebg")
//    Call<ResponseBody> getimage(
//            @Part MultipartBody.Part file);


    @Multipart
    @Headers({
            "Accept: application/json",
            "X-Api-Key: G5jQkXKqSXDBqPW5pgnRiCha",
            "X-RateLimit-Limit: 500",
            "X-RateLimit-Remaining: 10",
            "Retry-After: 5"
    })
    @POST("removebg")
    Call<ResponseBody> getimage(
            @Part MultipartBody.Part image,@Part("format") RequestBody format);

//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "X-Api-Key: 3FEjgq4wczoEQtYcAcsunzWF",
//    })
//    @POST("removebg")
//    Call<ResponseBody> getimage(
//            @Field("image_url") String url);

    @FormUrlEncoded
    @POST("createView360")
    Call<ResponseBody> uploadimage(@Field("media") String media);


}
