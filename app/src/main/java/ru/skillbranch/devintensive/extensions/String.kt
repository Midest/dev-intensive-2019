package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    var tr = this.trim()
    if( tr.length > length ) tr = tr.substring(0,length).trim() + "..."
    return tr
}

fun String.stripHtml(): String {
    return this
        .replace("""(<.*?>)|(&(amp|lt|gt|quot|#39);)""".toRegex(), "")
        .replace(""" +""".toRegex()," ")
}