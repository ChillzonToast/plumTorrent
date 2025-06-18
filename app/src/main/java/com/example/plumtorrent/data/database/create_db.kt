package com.example.plumtorrent.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.plumtorrent.models.Torrent
import com.example.plumtorrent.models.Converters

@Database(
    entities = [Torrent::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TorrentDatabase : RoomDatabase() {
    abstract fun torrentDao(): TorrentDao

    companion object {
        @Volatile
        private var INSTANCE: TorrentDatabase? = null

        fun getDatabase(context: Context): TorrentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TorrentDatabase::class.java,
                    "torrent_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}