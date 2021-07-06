package com.teamcatchme.add_action_seojin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonObject
import com.teamcatchme.add_action_seojin.utils.compressImageFile
import com.teamcatchme.add_action_seojin.utils.getBitmapFromUri
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ActivityAddActionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.AccessController.getContext

class AddActionActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private var imgPath: String? = null
    private var queryImageUrl: String? = null
    private lateinit var binding: ActivityAddActionBinding

    companion object {
        val baseUrl = "http://52.14.194.163:5000/"
    }

    private fun detachImage() {
        imageUri = null
        imgPath = null
        queryImageUrl = null
        binding.imageView.setImageResource(R.drawable.ic_camera_52)
        binding.btnCancelImg.visibility = View.GONE
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            RESULT_OK -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    lifecycleScope.launch(Dispatchers.Main.immediate) {
                        if (it.data?.data != null) {     //Photo from gallery
                            imageUri = it.data!!.data
                            queryImageUrl = imageUri?.path!!
                            queryImageUrl = compressImageFile(queryImageUrl!!, false, imageUri!!)
                        } else {
                            queryImageUrl = imgPath ?: ""
                            compressImageFile(queryImageUrl!!, uri = imageUri!!)
                        }
                        imageUri = Uri.fromFile(File(queryImageUrl!!))
                        Log.d("태그", "query Image Url $queryImageUrl")
                        if (queryImageUrl!!.isNotEmpty()) {
                            Glide.with(this@AddActionActivity)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .load(queryImageUrl)
                                .into(binding.imageView)
                            binding.btnCancelImg.visibility = View.VISIBLE
                        }
                    }
                } else {
                    lifecycleScope.launch(Dispatchers.Main.immediate) {
                        imageUri = it.data!!.data
                        Glide.with(this@AddActionActivity)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .load(imageUri)
                            .into(binding.imageView)
                        binding.btnCancelImg.visibility = View.VISIBLE
                    }
                }
            }
            RESULT_CANCELED -> {
                Log.d("태그", "Canceled")
            }
        }
    }

    private fun launchGalleryPicker() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        launchGalleryPicker()
                    }
                }
            }
        }
    }

    private fun sendImage() {
        if (imageUri != null) {
            val gson = GsonConverterFactory.create()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gson)
                .build()

            val targetString = "흑마법 드르릉"
            val textRequestBody: RequestBody =
                targetString.toRequestBody("text/plain".toMediaTypeOrNull())
            val textHashMap = HashMap<String, RequestBody>()
            textHashMap["text"] = textRequestBody

            val targetBitmap: Bitmap? = getBitmapFromUri(imageUri!!)
            val bitmapRequestBody: BitmapRequestBody? = targetBitmap?.let { BitmapRequestBody(it) }
            val bitmapMultipartBody: MultipartBody.Part? =
                bitmapRequestBody?.let { MultipartBody.Part.createFormData("image", "seojin", it) }

            val service = retrofit.create(PostImg::class.java)
            val call = bitmapMultipartBody?.let { service.postImg(it, textHashMap) }
            call?.enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    val responseBody = response.body()
                    Log.d("태그", "Success : $responseBody")
                    Toast.makeText(this@AddActionActivity, "Request Success", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.d("태그", "Failure : ${t.message}")
                    Toast.makeText(this@AddActionActivity, "Request Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddActionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setOnClickListener {
            launchGalleryPicker()
        }
        binding.btnCancelImg.setOnClickListener {
            detachImage()
        }
        binding.btnSendImg.setOnClickListener {
            sendImage()
        }
    }

}