package com.adityafakhri.medfluffy.presentation.ui.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.data.source.remote.response.MedfluffyResponse
import com.adityafakhri.medfluffy.data.source.remote.retrofit.ApiConfig
import com.adityafakhri.medfluffy.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadViewModel(val context: Context) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _resultPage = MutableLiveData<Boolean>()
    val resultPage: LiveData<Boolean> = _resultPage

    var error = MutableLiveData("")

    private val _result = MutableLiveData<MedfluffyResponse>()
    val result: LiveData<MedfluffyResponse> = _result

    fun uploadImage() {
        if (UploadActivity.getFile != null) {
            val file = reduceFileImage(UploadActivity.getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )
            _loading.value = true
            _resultPage.value = false
            val client = ApiConfig.getApiService().upload(imageMultipart)
            client.enqueue(object : Callback<MedfluffyResponse> {
                override fun onResponse(
                    call: Call<MedfluffyResponse>,
                    response: Response<MedfluffyResponse>
                ) {
                    if (response.isSuccessful) {
                        _result.value = response.body()
                        _resultPage.value = true
                    } else {
                        error.postValue("Error ${response.code()} : ${response.message()}")
                        _resultPage.value = false
                    }
                    _loading.value = false
                }

                override fun onFailure(call: Call<MedfluffyResponse>, t: Throwable) {
                    _loading.value = false
                    _resultPage.value = false
                    Log.e(TAG, "onFailure Call: ${t.message}")
                    error.postValue("${context.getString(R.string.error_upload)} : ${t.message}")
                }
            })
        }
    }

    companion object {
        const val TAG = "AddStoryViewModel"
    }
}