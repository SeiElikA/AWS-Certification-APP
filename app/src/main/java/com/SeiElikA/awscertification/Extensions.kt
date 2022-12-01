package com.SeiElikA.awscertification

class Extensions {
}

fun Int.formatZero(): String {
    return "%02d".format(this)
}

fun String.removeHtml(): String {
    return this.replace("<p>", "").replace("</p>", "")
}