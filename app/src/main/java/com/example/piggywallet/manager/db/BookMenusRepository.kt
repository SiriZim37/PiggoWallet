package com.example.piggywallet.manager.db

import androidx.lifecycle.LiveData
import com.example.piggywallet.manager.db.datamodel.BookMenus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookMenusRepository (private val masterbookmenusDao: BookMenusDao) {

    /**
     * Room is multithreading program, Observe LiveData will notify when data is changed
     * */

    //Not a private Variable
    val allBookMenus : LiveData<List<BookMenus>> = masterbookmenusDao.getAllBookMenus()

    fun insert(night: BookMenus){
        CoroutineScope(Dispatchers.IO).launch {
            masterbookmenusDao.insert(night)
        }

    }
}