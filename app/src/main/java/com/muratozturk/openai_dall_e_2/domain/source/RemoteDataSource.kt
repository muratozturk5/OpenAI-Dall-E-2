package com.muratozturk.openai_dall_e_2.domain.source

import com.muratozturk.openai_dall_e_2.data.model.GeneratedImage
import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun generateImage(requestBody: RequestBody): GeneratedImage
}