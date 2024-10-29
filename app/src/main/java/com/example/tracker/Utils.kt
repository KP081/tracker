package com.example.tracker

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun formateDateToHumanReadableForm(dateInMillis : Long) : String{
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy" , Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }

    fun formatToDecimalNumber(d : Double) : String{
        return String.format("%.2f" , d)
    }

    fun getMillisFormDate(date: String) : Long{
        return getMilliFormDate(date)
    }
    fun getMilliFormDate(dateFormat : String?) : Long{
        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            date = formatter.parse(dateFormat)
        }
        catch (e : ParseException){
            e.printStackTrace()
        }
        println("Today is $date")
        return date.time
    }
}