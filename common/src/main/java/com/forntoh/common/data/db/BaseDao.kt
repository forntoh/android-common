package com.forntoh.common.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entity: T)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun updateAll(vararg entity: T)

    @Delete
    suspend fun delete(entity: T)
}