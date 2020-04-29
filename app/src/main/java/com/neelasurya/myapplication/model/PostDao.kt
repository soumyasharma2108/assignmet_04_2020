package com.neelasurya.myapplication.model

import androidx.room.*

@Dao
interface PostDao {
    @get:Query("SELECT * FROM results")
    val all: List<Results>

    @Query("DELETE FROM results")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(result: ArrayList<Results>)

    @Update
    fun updateResult(result: Results)
}