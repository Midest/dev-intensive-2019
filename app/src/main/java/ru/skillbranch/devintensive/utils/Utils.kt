package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName:  String?):  Pair<String?, String?> {
        if( fullName == null || fullName.isBlank()) return Pair(null,null)

        val parts:  List<String>? = fullName.split(" ")
        val firstName = parts?.getOrNull(0 )
        val lastName = parts?.getOrNull(1 )
        return Pair(firstName,lastName)
    }

    fun toInitials(firstName:  String?, lastName:  String?):  String?{
        fun firstCharUpperOrNull(s:  String?):  String? =
            if( s == null || s.isBlank()) null else s.trim()[0].toUpperCase().toString()
        val f = firstCharUpperOrNull( firstName )
        val s = firstCharUpperOrNull( lastName )
        return if( f == null && s == null ) null else ((f ?: "") + (s ?: ""))
    }

    fun transliteration(payload: String, divider: String = " "): String{
        val trMapL = mapOf(
            "а" to  "a", "б" to  "b", "в" to  "v", "г" to  "g", "д" to  "d", "е" to  "e", "ё" to  "e",
            "ж" to  "zh", "з" to  "z", "и" to  "i", "й" to  "i", "к" to  "k", "л" to  "l", "м" to  "m",
            "н" to  "n", "о" to  "o", "п" to  "p", "р" to  "r", "с" to  "s", "т" to  "t", "у" to  "u",
            "ф" to  "f", "х" to  "h", "ц" to  "c", "ч" to  "ch", "ш" to  "sh", "щ" to  "sh'", "ъ" to  "",
            "ы" to  "i", "ь" to  "", "э" to  "e", "ю" to  "yu", "я" to  "ya"
        )
        var trMap = mutableMapOf( " " to divider )
        trMap.putAll( trMapL )
        for( (k,v) in trMapL.entries ){
            trMap[k.toUpperCase()] =    if( v.isEmpty()) "" else v[0].toUpperCase() +
                                        if( v.length > 1 ) v.substring(1) else ""
        }

        return payload.map { c -> trMap[c.toString()] ?: c.toString() }.joinToString( separator = "", prefix = "", postfix = "")
    }
}