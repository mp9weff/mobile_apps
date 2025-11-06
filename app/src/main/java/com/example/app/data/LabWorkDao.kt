package com.example.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LabWorkDao {
    @Query("SELECT * FROM lab_works WHERE subjectId = :subjectId ORDER BY id")
    fun observeBySubject(subjectId: Long): Flow<List<LabWork>>

    @Query("SELECT * FROM lab_works WHERE id = :id")
    fun observeById(id: Long): Flow<LabWork?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lab: LabWork): Long

    @Update
    suspend fun update(lab: LabWork)

    @Delete
    suspend fun delete(lab: LabWork)
}


