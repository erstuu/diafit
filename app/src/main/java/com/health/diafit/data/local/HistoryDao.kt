package com.health.diafit.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.health.diafit.models.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getHistories(): LiveData<List<HistoryEntity>>

    @Query("SELECT blood_glucose_level, HbA1c_level, diabetes_category FROM history ORDER BY check_date DESC LIMIT 1")
    fun getCurrentHistory(): LiveData<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistories(history: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Query("DELETE FROM history")
    suspend fun deleteHistories()
}