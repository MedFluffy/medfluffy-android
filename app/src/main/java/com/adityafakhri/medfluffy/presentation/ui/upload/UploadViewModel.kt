package com.adityafakhri.medfluffy.presentation.ui.upload

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.core.data.source.remote.response.UploadResponse
import com.adityafakhri.medfluffy.core.data.source.remote.retrofit.ApiConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadViewModel(val context: Context) : ViewModel() {
    var loading = MutableLiveData(View.GONE)
    var error = MutableLiveData("")
    var isSuccessUpload = MutableLiveData(false)

    fun uploadImage(
        file: File,
    ) {
        loading.postValue(View.VISIBLE)

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val client = ApiConfig.getApiService().upload(imageMultipart)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    isSuccessUpload.postValue(true)
                } else {
                    error.postValue("Error ${response.code()} : ${response.message()}")
                }
                loading.postValue(View.GONE)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                loading.postValue(View.GONE)
                Log.e(TAG, "onFailure Call: ${t.message}")
                error.postValue("${context.getString(R.string.error_upload)} : ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "AddStoryViewModel"
    }
}