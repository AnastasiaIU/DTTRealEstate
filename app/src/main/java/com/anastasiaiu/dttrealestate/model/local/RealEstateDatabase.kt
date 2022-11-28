package com.anastasiaiu.dttrealestate.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anastasiaiu.dttrealestate.model.Constants.DATABASE_NAME
import com.anastasiaiu.dttrealestate.model.House

@Database(entities = [House::class], version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    companion object {

        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun getInstance(context: Context): RealEstateDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RealEstateDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}