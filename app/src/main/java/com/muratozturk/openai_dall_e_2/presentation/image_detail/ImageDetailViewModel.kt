package com.muratozturk.openai_dall_e_2.presentation.image_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.openai_dall_e_2.domain.use_case.DownloadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val downloadImageUseCase: DownloadImageUseCase
) : ViewModel() {

    var state: LiveData<String> = downloadImageUseCase.downloadImageState

    fun downloadImage(url: String) {
        downloadImageUseCase(url)
    }

}