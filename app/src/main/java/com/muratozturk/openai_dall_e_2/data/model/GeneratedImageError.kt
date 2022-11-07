package com.muratozturk.openai_dall_e_2.data.model


import com.google.gson.annotations.SerializedName

data class GeneratedImageError(
    @SerializedName("error")
    val error: Error
)