package com.example.piggywallet

import android.app.Application
import com.example.piggywallet.manager.ContextManager
import com.example.piggywallet.manager.db.DatabaseManager
import com.example.piggywallet.manager.db.RoomDatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application(){


    val applicationScope = CoroutineScope(SupervisorJob())
    init {
        //annotate Dao that want to use and get Instance of Dao from Database
        ContextManager.getInstance().setApplicationContext(this)
//        RoomDatabaseManager.getInstance(this)
//        DatabaseManager.initDB(this)
    }

//    val sleepNightDao = RoomDatabaseManager.getInstance(this).masterBookMenuDao()
//    val database by lazy {
//        RoomDatabaseManager.getDatabase(this, applicationScope)
//    }
//    val repository by lazy {
//        BookMenusRepository( database.masterBookMenuDao())
//    }
}