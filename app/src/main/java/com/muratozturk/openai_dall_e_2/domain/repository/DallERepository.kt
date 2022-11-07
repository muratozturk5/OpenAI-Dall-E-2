package com.muratozturk.openai_dall_e_2.domain.repository


import com.muratozturk.openai_dall_e_2.common.Resource
import com.muratozturk.openai_dall_e_2.data.model.GeneratedImage
import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import kotlinx.coroutines.flow.Flow

interface DallERepository {
    fun generateImage(requestBody: RequestBody): Flow<Resource<GeneratedImage>>
}