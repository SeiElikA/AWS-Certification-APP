package com.SeiElikA.awscertification.Data

import com.google.gson.annotations.SerializedName

data class Choices(
    @SerializedName("A") var A: String? = null,
    @SerializedName("B") var B: String? = null,
    @SerializedName("C") var C: String? = null,
    @SerializedName("D") var D: String? = null,
    @SerializedName("E") var E: String? = null
): java.io.Serializable