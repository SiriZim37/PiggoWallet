package com.example.piggywallet.manager.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.piggywallet.manager.ContextManager
import com.example.piggywallet.manager.db.datamodel.BookMenus

class DatabaseManager {


//    fun initDB(context: Context){
//        val dbDao = RoomDatabaseManager.getInstance(context).masterBookMenuDao()
//        val repo = BookMenusRepository(dbDao)
////        allBookMenus = repo.allBookMenus
//    }

//    fun getBooksMenu(): LiveData<List<BookMenus>> {
//        val context = ContextManager.getInstance().getApplicationContext()
//        val allBookMenus: LiveData<List<BookMenus>>
//        val dbDao = RoomDatabaseManager.getInstance(context).masterBookMenuDao()
//        val repo = BookMenusRepository(dbDao)
//        allBookMenus = repo.allBookMenus
//        return allBookMenus
//    }

    companion object {
        private val databaseManager = DatabaseManager()

        fun getInstance() = databaseManager
    }
}