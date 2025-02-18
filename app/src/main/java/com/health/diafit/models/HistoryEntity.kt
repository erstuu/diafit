package com.health.diafit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name="check_date")
    val checkDate: String? = null,

    @ColumnInfo(name="blood_glucose_level")
    val bloodGlucoseLevel: Int? = null,

    @ColumnInfo(name="HbA1c_level")
    val hbA1cLevel: Float? = null,

    @ColumnInfo(name="diabetes_category")
    val diabetesCategory: String? = null,
)