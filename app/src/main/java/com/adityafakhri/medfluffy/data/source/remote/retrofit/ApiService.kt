package com.adityafakhri.medfluffy.data.source.remote.retrofit

import com.adityafakhri.medfluffy.data.source.remote.response.MedfluffyResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    fun upload(
        @Part file: MultipartBody.Part,
    ): Call<MedfluffyResponse>
}