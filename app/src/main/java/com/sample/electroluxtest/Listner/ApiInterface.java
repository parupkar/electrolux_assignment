package com.sample.electroluxtest.Listner;

import com.sample.electroluxtest.Helper.Constant;
import com.sample.electroluxtest.Model.ConnectionResponse;
import com.sample.electroluxtest.Model.GetFlickerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(Constant.CONNECTION)
    Call<ConnectionResponse> Connection();

    @GET(Constant.BASE_URL)
    Call<GetFlickerResponse> GetFlickerData(@Query("api_key") String api_key, @Query("method") String method, @Query("tags") String tags, @Query("format") String format, @Query("nojsoncallback") String nojsoncallback, @Query("aextras") String aextras, @Query("extras") String extras_sq, @Query("extras") String extras_m, @Query("per_page") int per_page, @Query("page") int page);

}
