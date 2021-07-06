package com.teamcatchme.add_action_seojin

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddActionViewModel : ViewModel() {
    private val _imageUri = MutableLiveData<Uri>()
    val imageUri : LiveData<Uri> get() = _imageUri

    fun setImageUri(uri: Uri) = viewModelScope.launch {
        _imageUri.value = uri
    }

}

/*

// 사용 시 - kotlin 1.3.x 버전까지는 Observer을 명시해야 한다.
// Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
loadDetailViewModel.liveData.observe(this, Observer {
    // View 처리
})

// kotlin 1.4.x 버전부터는 SAM 지원으로 아래와 같다.
// Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
loadDetailViewModel.liveData.observe(this) {
    // View 처리
}
 */