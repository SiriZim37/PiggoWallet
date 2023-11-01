package com.example.piggywallet.manager.db

import androidx.lifecycle.LiveData
import com.example.piggywallet.manager.db.datamodel.BookMenus
import com.example.piggywallet.manager.db.datamodel.BookNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomDBRepository (private val roomDBDao: RoomDBDao) {

    /**
     * Room is multithreading program, Observe LiveData will notify when data is changed
     * */

    //Not a private Variable
    val allBookMenus : LiveData<List<BookMenus>> = roomDBDao.getAllBookMenus()

    fun insert(night: BookMenus){
        CoroutineScope(Dispatchers.IO).launch {
            roomDBDao.insert(night)
        }
    }

    val allInOutData : LiveData<List<BookNote>> = roomDBDao.getAllInAndOutCome()
    val allIncomeList : LiveData<List<BookNote>> = roomDBDao.getAllIncomeData()
    val allOutcomeList : LiveData<List<BookNote>> = roomDBDao.getAllOutComeData()
    val incomeTotal : LiveData<String> = roomDBDao.getIncomeTotal()
    val outcomeTotal : LiveData<String> = roomDBDao.getOutComeTotal()

    fun insertInAndOutCome(inout: BookNote){
        CoroutineScope(Dispatchers.IO).launch {
            roomDBDao.insertInAndOutCome(inout)
        }
    }

}