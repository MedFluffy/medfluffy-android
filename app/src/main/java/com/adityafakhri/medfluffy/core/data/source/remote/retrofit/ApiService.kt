package com.adityafakhri.medfluffy.core.data.source.remote.retrofit

import com.adityafakhri.medfluffy.core.data.source.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("stories")
    fun upload(
        @Part file: MultipartBody.Part
    ): Call<UploadResponse>
}