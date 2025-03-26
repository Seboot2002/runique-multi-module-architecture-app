package com.sebascamayo.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebascamayo.core.database.dao.RunDao
import com.sebascamayo.core.database.dao.RunPendingSyncDao
import com.sebascamayo.core.database.entity.DeletedRunSyncEntity
import com.sebascamayo.core.database.entity.RunEntity
import com.sebascamayo.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
               ],
    version = 1
)
abstract class RunDatabase: RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}