package com.example.monsterhunterarmor.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ArmorEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArmorDatabase : RoomDatabase() {
    abstract fun armorDao(): ArmorDao

    companion object {
        @Volatile
        private var INSTANCE: ArmorDatabase? = null

        fun getDatabase(context: Context): ArmorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArmorDatabase::class.java,
                    "armor_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}