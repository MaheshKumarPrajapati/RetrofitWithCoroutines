package com.mahesh_prajapati.itunes.utils

import java.text.SimpleDateFormat
import java.util.*

class HelperMethods {
    fun milliSecondToDuration(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return "$minutes:$seconds Minutes"
    }

    fun parseDate(date:String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC");
        val outputFormat = SimpleDateFormat("MMMM dd,yyyy hh:mm:ss a")
        val parsedDate: Date = inputFormat.parse(date)
        val formattedDate: String = outputFormat.format(parsedDate)
        return formattedDate

    }
}