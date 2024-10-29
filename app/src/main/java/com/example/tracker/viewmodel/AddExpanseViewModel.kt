package com.example.tracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracker.data.dao.ExpanseDao
import com.example.tracker.data.modul.ExpanseDataBase
import com.example.tracker.data.modul.ExpanseEntity

class AddExpanseViewModel(val dao: ExpanseDao) : ViewModel() {

    suspend fun addExpanse(expanseEntity: ExpanseEntity) : Boolean {
        return try {
            dao.insertExpense(expanseEntity)
            true
        }
        catch (ex : Throwable){
            false
        }
    }
}

class AddExpanseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(AddExpanseViewModel::class.java)) {
            val dao = ExpanseDataBase.getDatabase(context).expanseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpanseViewModel(dao) as T
        }
        throw IllegalArgumentException("unknown viewmodel class")
    }
}