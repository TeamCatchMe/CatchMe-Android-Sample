package com.teamcatchme.add_action_seojin

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink

class AddActionViewModel : ViewModel() {
    private val _postSuccessResponse = MutableLiveData<PostResponse>()
    val postSuccessResponse: LiveData<PostResponse> get() = _postSuccessResponse
    private val _postFailureResponse = MutableLiveData<Boolean>()
    val postFailureResponse: LiveData<Boolean> get() = _postFailureResponse

    fun sendPostRequest(bitmap: Bitmap?, targetString: String) {
        val textRequestBody: RequestBody =
            targetString.toRequestBody("text/plain".toMediaTypeOrNull())
        val textHashMap = HashMap<String, RequestBody>()
        textHashMap["text"] = textRequestBody

        val bitmapRequestBody: BitmapRequestBody? = bitmap?.let { BitmapRequestBody(it) }
        val bitmapMultipartBody: MultipartBody.Part =
            bitmapRequestBody.let { MultipartBody.Part.createFormData("image", "seojin", it!!) }
        viewModelScope.launch {
            val response = bitmapMultipartBody.let {
                RetrofitServiceCreator.postImgService.postImg(
                    it, textHashMap
                )
            }
            if (response.isSuccessful) {
                _postSuccessResponse.postValue(response.body())
            } else {
                _postFailureResponse.postValue(true)
            }
        }
    }

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }

}