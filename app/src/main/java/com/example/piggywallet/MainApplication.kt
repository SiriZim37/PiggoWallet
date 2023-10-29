package com.example.piggywallet

import android.app.Application
import com.example.piggywallet.manager.db.BookMenusRepository
import com.example.piggywallet.manager.db.RoomDatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
//    val sleepNightDao = RoomDatabaseManager.getInstance(this).masterBookMenuDao()
//    val database by lazy {
//        RoomDatabaseManager.getDatabase(this, applicationScope)
//    }
//    val repository by lazy {
//        BookMenusRepository( database.masterBookMenuDao())
//    }
}