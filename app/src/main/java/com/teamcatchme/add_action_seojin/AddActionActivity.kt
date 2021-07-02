package com.teamcatchme.add_action_seojin

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.system.Os.close
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.teamcatchme.add_action_seojin.utils.compressImageFile
import com.teamcatchme.catchmesample.BuildConfig
import com.teamcatchme.catchmesample.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class AddActionActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private var imgPath: String? = null
    private var queryImageUrl: String? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            Log.d("태그", "Came Back to Activity")
            GlobalScope.launch(Dispatchers.Main) {
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
                        .into(findViewById(R.id.imageView))
                }
            }
        } else if (it.resultCode == RESULT_CANCELED) {
            Log.d("태그", "Canceled")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun launchGalleryPicker() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()) {
                    launchGalleryPicker()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_action)
        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            launchGalleryPicker()
        }
    }

}