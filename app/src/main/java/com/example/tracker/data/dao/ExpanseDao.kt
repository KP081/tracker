package com.example.tracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tracker.data.modul.ExpanseEntity
import java.util.concurrent.Flow

@Dao
interface ExpanseDao {

    @Query("Select * from expanse_table")
    fun getAllExpanses(): kotlinx.coroutines.flow.Flow<List<ExpanseEntity>>

    @Query("Select type , date , SUM(amount) AS total_amount from expanse_table where type = :type Group by date")
    fun getAllExpanseByDate(type: String = "Expanse"): kotlinx.coroutines.flow.Flow<List<ExpenseSummary>>

    @Insert
    suspend fun insertExpense(expanseEntity: ExpanseEntity)

    @Delete
    suspend fun deleteExpanse(expanseEntity: ExpanseEntity)

    @Update
    suspend fun updateExpanse(expanseEntity: ExpanseEntity)
}