package com.muratozturk.openai_dall_e_2.data.repository


import com.muratozturk.openai_dall_e_2.common.Resource
import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import com.muratozturk.openai_dall_e_2.domain.repository.DallERepository
import com.muratozturk.openai_dall_e_2.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.flow

class DallERepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DallERepository {
    override fun generateImage(
        requestBody: RequestBody
    ) = flow {
        emit(Resource.Loading)

        try {
            val response = remoteDataSource.generateImage(requestBody)
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }

    }

}