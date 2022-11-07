package com.muratozturk.openai_dall_e_2.data.model


import com.google.gson.annotations.SerializedName

data class RequestBody(
    @SerializedName("n")
    val n: Int,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("size")
    val size: String
)