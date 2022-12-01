package com.SeiElikA.awscertification.Data

import com.google.gson.annotations.SerializedName


data class Question(
    @SerializedName("id") var id: String? = null,
    @SerializedName("eid") var eid: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("choices") var choices: Choices? = Choices(),
    @SerializedName("correct_ans") var correctAns: String? = null,
    @SerializedName("ans_desc") var ansDesc: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("qimage") var qimage: String? = null,
    @SerializedName("qaudio") var qaudio: String? = null,
    @SerializedName("a_type") var aType: String? = null,
    @SerializedName("aimage") var aimage: String? = null,
    @SerializedName("analysis_audio") var analysisAudio: String? = null,
    @SerializedName("wrong_have") var wrongHave: String? = null,
    @SerializedName("showAnswer") var showAnswer: Boolean? = null
): java.io.Serializable