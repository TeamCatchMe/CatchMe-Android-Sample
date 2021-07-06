package com.teamcatchme.add_action_seojin

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.teamcatchme.add_action_seojin.utils.*
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ActivityAddActionBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink

class AddActionActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private var queryImageUrl: String? = null
    private lateinit var binding: ActivityAddActionBinding
    private var bitmap: Bitmap? = null
    private val addActionViewModel: AddActionViewModel by viewModels()

    private fun detachImage() {
        imageUri = null
        queryImageUrl = null
        binding.imageView.setImageResource(R.drawable.ic_camera_52)
        binding.btnCancelImg.visibility = View.GONE
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            RESULT_OK -> {
                lifecycleScope.launch {
                    imageUri = it.data!!.data
                    if (imageUri == null) {
                        return@launch
                    }
                    val (hgt, wdt) = getImageHgtWdt(imageUri!!)
                    bitmap = decodeFile(
                        this@AddActionActivity, imageUri!!, wdt, hgt, ScalingLogic.FIT
                    )
                    binding.imageView.setImageBitmap(bitmap)
                    binding.btnCancelImg.visibility = View.VISIBLE
                    /* 위의 코드가 안 될 시 아래 코드로 ㄱㄱ
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                            compressImageFile(imageUri?.path!!, false, bitmap!!)
                            imageUri = Uri.fromFile(File(queryImageUrl!!))
                            Log.d("태그", "query Image Url $queryImageUrl")
                            if (queryImageUrl!!.isNotEmpty()) {
                                Glide.with(this@AddActionActivity)
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .load(queryImageUrl)
                                    .into(binding.imageView)
                            }
                        }
                        else -> {
                            Glide.with(this@AddActionActivity)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .load(imageUri)
                                .into(binding.imageView)
                        }
                    }*/
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
            val targetString = "흑마법 드르릉"
            registerObserver()
            addActionViewModel.sendPostRequest(bitmap, targetString)
        }
    }

    private fun registerObserver() {
        addActionViewModel.postSuccessResponse.observe(this, Observer {
            Log.d("태그", "$it")
        })
        addActionViewModel.postFailureResponse.observe(this, Observer {
            Log.d("태그", "$it")
        })
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