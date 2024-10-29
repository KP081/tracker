package com.example.tracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracker.R
import com.example.tracker.Utils
import com.example.tracker.data.dao.ExpanseDao
import com.example.tracker.data.modul.ExpanseDataBase
import com.example.tracker.data.modul.ExpanseEntity

class HomeViewModel(dao: ExpanseDao) : ViewModel() {
    val expanse = dao.getAllExpanses()

    fun getBalance(list: List<ExpanseEntity>) : String{

        var balance = 0.0
        list.forEach{
            if(it.type == "Income"){
                balance += it.amount
            }
            else{
                balance -= it.amount
            }
        }
        return "$ ${Utils.formatToDecimalNumber(balance)}"
    }

    fun getTotalExpanse(list: List<ExpanseEntity>) : String{

        var total = 0.0
        list.forEach{
            if(it.type == "Expense"){
                total += it.amount
            }
        }
        return "$ ${Utils.formatToDecimalNumber(total)}"
    }

    fun getTotalIncome(list: List<ExpanseEntity>) : String{
        var totalIncome = 0.0
        list.forEach{
            if(it.type == "Income"){
                totalIncome += it.amount
            }
        }
        return "$ ${Utils.formatToDecimalNumber(totalIncome)}"
    }

    fun getItemIcone(item : ExpanseEntity) : Int{
        if(item.category == "Netflix"){
            return R.drawable.netfix
        }
        else if (item.category == "Paypal"){
            return R.drawable.paypal
        }
        else if (item.category == "Starbucks"){
            return R.drawable.starbucks
        }
        else{
            return R.drawable.upwork
        }
    }
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpanseDataBase.getDatabase(context).expanseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}