package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format( pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add( value: Int, units: TimeUnits = TimeUnits.SECOND ): Date {
    var time = this.time
    time += when( units ){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(d: Date = Date()): String{
    val diff = this.time - d.time
    val absDiff = Math.abs( diff )
    fun f( s: String ): String{
        return if( diff > 0 ) "через $s" else "$s назад"
    }
    return when( absDiff ){
        in 0..SECOND                       -> "только что"
        in SECOND .. 45 * SECOND             -> f("несколько секунд")
        in 45 * SECOND .. 75 * SECOND        -> f("минуту")
        in 75 * SECOND .. 45 * MINUTE        -> f("${absDiff/ MINUTE} ${TimeUnits.MINUTE.declensionForm(absDiff/ MINUTE)}")
        in 45 * MINUTE .. 75 * MINUTE        -> f("час")
        in 75 * MINUTE .. 22 * HOUR          -> f("${absDiff/ HOUR} ${TimeUnits.HOUR.declensionForm(absDiff/ HOUR)}")
        in 22 * HOUR .. 26 * HOUR            -> f("день")
        in 26 * HOUR .. 360 * DAY            -> f("${absDiff/ DAY} ${TimeUnits.DAY.declensionForm(absDiff/ DAY)}")
        else -> if( diff > 0 ) "более чем через год" else "более года назад"
    }
}

enum class TimeUnits(
    private val v1: String = "",
    private val v2_4: String = "",
    private val ve: String = ""
) {
    SECOND,
    MINUTE("минута", "минуты", "минут"),
    HOUR("час", "часа", "часов"),
    DAY("день", "дня", "дней");

    fun declensionForm(amount: Long): String = when(amount%10){
        1L           -> v1
        in 2..4     -> v2_4
        else        -> ve
    }
}