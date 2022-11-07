package com.muratozturk.openai_dall_e_2.domain.use_case

import android.app.Application
import com.muratozturk.openai_dall_e_2.common.DownloadImage
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(private val application: Application) :
    DownloadImage() {
    operator fun invoke(url: String) = downloadImageFromURL(url, application)

}