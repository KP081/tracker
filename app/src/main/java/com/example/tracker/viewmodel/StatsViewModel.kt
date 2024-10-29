package com.example.tracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.DeleteColumn
import com.example.tracker.R
import com.example.tracker.Utils
import com.example.tracker.data.dao.ExpanseDao
import com.example.tracker.data.modul.ExpanseDataBase
import com.example.tracker.data.modul.ExpanseEntity
import java.security.KeyStore
import kotlin.collections.Map.Entry

class StatsViewModel(dao: ExpanseDao) : ViewModel() {
    val entries = dao.getAllExpanseByDate()

    fun getEntriesForChart(entries: List<ExpanseEntity>): List<com.github.mikephil.charting.data.Entry> {
        val list = mutableListOf<com.github.mikephil.charting.data.Entry>()

        for(entry in entries){
            val formattedDate = Utils.getMillisFormDate(entry.date.toString())
            list.add(com.github.mikephil.charting.data.Entry(formattedDate.toFloat() , entry.amount.toFloat()))
        }

        return list
    }
}


class StatsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            val dao = ExpanseDataBase.getDatabase(context).expanseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("unknown viewmodel class")
    }
}