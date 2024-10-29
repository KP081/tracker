package com.example.tracker.data.modul

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tracker.data.dao.ExpanseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ExpanseEntity::class] , version = 1)
    abstract class ExpanseDataBase : RoomDatabase(){
    abstract fun expanseDao(): ExpanseDao

    companion object{
        const val DATABASE_NAME = "expanse_database"

        @JvmStatic
        fun getDatabase(context: Context) : ExpanseDataBase {
            return Room.databaseBuilder(
                context,
                ExpanseDataBase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    InitBasicDate(context)
                }

                fun InitBasicDate(context: Context){
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = getDatabase(context).expanseDao()
                        dao.insertExpense(ExpanseEntity(1 , "Salary" , 5000.02 , System.currentTimeMillis() , "Salary" , "Income"))
                        dao.insertExpense(ExpanseEntity(2 , "Paypal" , 364.32 , System.currentTimeMillis() , "Paypal" , "Income"))
                        dao.insertExpense(ExpanseEntity(3 , "Netflix" , 300.43 , System.currentTimeMillis() , "Netflix" , "Expanse"))
                        dao.insertExpense(ExpanseEntity(4 , "Starbucks" , 4000.03 , System.currentTimeMillis() , "Starbucks" , "Expanse"))
                    }
                }
            }).build()
        }
    }
}
