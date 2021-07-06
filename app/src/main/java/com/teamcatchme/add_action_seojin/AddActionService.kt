package com.teamcatchme.add_action_seojin

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface PostImg {
    @Multipart
    @POST("post")
    fun postImg(
        @Part postImg: MultipartBody.Part,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<PostResponse>
}

data class PostResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PostResponseData
)

data class PostResponseData(
    val text: String,
    val image: String
)