package com.anastasiaiu.dttrealestate.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.model.ModelConstants.DATABASE_NAME

/**
 * [RealEstateDatabase] defines the database configuration and
 * serves as the app's main access point to the persisted data.
 */
@Database(entities = [House::class], version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    companion object {

        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        /**
         * Returns an instance of the [RealEstateDatabase].
         */
        fun getInstance(context: Context): RealEstateDatabase {

            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): RealEstateDatabase {

            return Room
                .databaseBuilder(
                    context.applicationContext,
                    RealEstateDatabase::class.java,
                    DATABASE_NAME
                )
                .build()
        }
    }
}