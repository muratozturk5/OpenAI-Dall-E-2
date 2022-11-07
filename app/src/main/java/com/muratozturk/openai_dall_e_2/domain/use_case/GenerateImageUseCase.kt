package com.muratozturk.openai_dall_e_2.domain.use_case

import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import com.muratozturk.openai_dall_e_2.domain.repository.DallERepository
import javax.inject.Inject

class GenerateImageUseCase @Inject constructor(private val repository: DallERepository) {
    operator fun invoke(requestBody: RequestBody) = repository.generateImage(requestBody)
}