package com.example.piggywallet.module.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.piggywallet.manager.ContextManager
import com.example.piggywallet.manager.db.RoomDBRepository
import com.example.piggywallet.manager.db.RoomDatabaseManager
import com.example.piggywallet.manager.db.datamodel.BookMenus
import com.example.piggywallet.manager.db.datamodel.BookNote
import com.example.piggywallet.module.book.BookDetailViewModel

class BookMainViewModel : ViewModel() {

    val context = ContextManager.getInstance().getApplicationContext()
    private  val repository:RoomDBRepository
    val allInOutMainBook: LiveData<List<BookNote>>
    val allIncomeMainBook: LiveData<List<BookNote>>
    val allOutcomeMainBook: LiveData<List<BookNote>>
    val inComeTotal: LiveData<String>
    val outComeTotal: LiveData<String>
    val whenDataLoadedInOutMainBookByDate = MutableLiveData<List<BookNote>>()
    val whenDataLoadedBook = MutableLiveData<List<BookNote>>()

    init {
        //annotate Dao that want to use and get Instance of Dao from Database
        RoomDatabaseManager.getInstance(context)
        val bookmenusdao = RoomDatabaseManager.getInstance(context).roomDBDao()
        repository = RoomDBRepository(bookmenusdao)
        allInOutMainBook =  repository.allInOutData
//        InOutMainBookByDate =  repository.InOutOnlyByDate()
        allIncomeMainBook = repository.allIncomeList
        allOutcomeMainBook = repository.allOutcomeList
        inComeTotal = repository.incomeTotal
        outComeTotal = repository.outcomeTotal
    }

    fun InOutOnlyByDate( date : String , item : ArrayList<BookNote>)   {
       val newItem =  item.filter { it.date == date }.sortedBy { date }
        whenDataLoadedInOutMainBookByDate.postValue(newItem)
    }

    fun getSearchBook(text: String  , item : ArrayList<BookNote>) {
        val newList = ArrayList(item!!.filter {
            it.menuName.contains(text)
        })
        whenDataLoadedBook.postValue(ArrayList(newList))
    }



}