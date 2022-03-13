package com.example.bizomapplication.globals
/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import java.text.SimpleDateFormat
import java.util.*
/**
 * This object class is responsible for to access the fields and methods
 * inside of it globally
* */
object GlobalUtils {
    private const val dateFormat : String = "yyyy-MM-dd"

    /**
     * This method is response to generate the current date in dateFormat
     * This method returns the current date as String value
     * */
    fun getCurrentDate() : String{
        val simpleDate = SimpleDateFormat(dateFormat)
        return simpleDate.format(Date())
    }

}