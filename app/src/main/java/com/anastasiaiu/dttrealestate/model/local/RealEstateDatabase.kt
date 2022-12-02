package com.anastasiaiu.dttrealestate.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anastasiaiu.dttrealestate.model.ModelConstants.DATABASE_NAME
import com.anastasiaiu.dttrealestate.model.House
import kotlinx.coroutines.CoroutineScope

/**
 * [RealEstateDatabase] defines the database configuration and
 * serves as the app's main access point to the persisted data.
 */
@Database(entities = [House::class], version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    private class HouseDatabaseCallback(private val scope: CoroutineScope) : Callback()

    companion object {

        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun getInstance(
            context: Context, scope: CoroutineScope
        ): RealEstateDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): RealEstateDatabase {
            return Room
                .databaseBuilder(
                    context.applicationContext,
                    RealEstateDatabase::class.java,
                    DATABASE_NAME
                )
                .addCallback(HouseDatabaseCallback(scope))
                .build()
        }
    }
}