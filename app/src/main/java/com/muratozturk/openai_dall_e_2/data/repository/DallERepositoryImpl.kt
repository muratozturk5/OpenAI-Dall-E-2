package com.muratozturk.openai_dall_e_2.data.repository


import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.muratozturk.openai_dall_e_2.common.Constants
import com.muratozturk.openai_dall_e_2.common.Resource
import com.muratozturk.openai_dall_e_2.data.model.RequestBody
import com.muratozturk.openai_dall_e_2.domain.repository.DallERepository
import com.muratozturk.openai_dall_e_2.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.flow
import java.io.File

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