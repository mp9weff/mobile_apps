package com.example.app.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStatus(value: LabStatus?): String? = value?.name

    @TypeConverter
    fun toStatus(value: String?): LabStatus? = value?.let { LabStatus.valueOf(it) }
}


