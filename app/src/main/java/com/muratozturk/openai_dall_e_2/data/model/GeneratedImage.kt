package com.muratozturk.openai_dall_e_2.data.model


import com.google.gson.annotations.SerializedName

data class GeneratedImage(
    @SerializedName("created")
    val created: Int,
    @SerializedName("data")
    val `data`: List<Data>
)