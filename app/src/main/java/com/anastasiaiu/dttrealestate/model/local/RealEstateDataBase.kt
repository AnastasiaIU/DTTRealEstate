package com.anastasiaiu.dttrealestate.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anastasiaiu.dttrealestate.model.Constants.DATABASE_NAME
import javax.inject.Inject

@Database(entities = [HouseEntity::class], version = 1, exportSchema = false)
abstract class RealEstateDataBase /*@Inject constructor()*/ : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    companion object {

        @Volatile
        private var INSTANCE: RealEstateDataBase? = null

        fun getInstance(context: Context): RealEstateDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RealEstateDataBase::class.java,
            DATABASE_NAME
        ).build()
    }
}