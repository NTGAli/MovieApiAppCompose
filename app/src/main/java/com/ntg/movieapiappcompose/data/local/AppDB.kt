package com.ntg.movieapiappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ntg.movieapiappcompose.data.model.RemoteKeys

@Database(entities = [MovieEntity::class, RemoteKeys::class], version = 2)
abstract class AppDB: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}