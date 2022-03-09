package de.mobilecompass.anappoficeandfire.modules.houses.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

@OptIn(ExperimentalStdlibApi::class)
class HouseDBConverters {

    companion object {
        val LOG_TAG: String = HouseDBConverters::class.java.simpleName

        val moshi: Moshi = Moshi.Builder().build()
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        val list = list ?: return null
        val adapter = moshi.adapter<List<String>>()
        return adapter.toJson(list)
    }

    @TypeConverter
    fun fromString(string: String?): List<String>? {
        val string = string ?: return null
        val adapter = moshi.adapter<List<String>>()
        return adapter.fromJson(string)
    }

}