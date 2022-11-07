package com.muratozturk.openai_dall_e_2.common

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import java.io.File

abstract class DownloadImage {

    private var msg: String? = ""
    private var lastMsg = ""

    val downloadImageState = MutableLiveData("")

    @SuppressLint("Range")
    fun downloadImageFromURL(url: String, context: Context) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)
        val title = getRandomString()
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("dall_e_$title.jpg")
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    "dall_e_$title.jpg"
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(directory, status)
                if (msg != lastMsg) {
                    downloadImageState.postValue(msg)
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }.start()

    }

    private fun getRandomString(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..5)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun statusMessage(directory: File, status: Int): String {
        val msg: String = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in $directory" + File.separator + "dall_e"
            else -> "There's nothing to download"
        }
        return msg
    }


}