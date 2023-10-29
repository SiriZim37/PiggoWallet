package com.example.piggywallet.module.book


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.piggywallet.manager.db.BookMenusRepository
import com.example.piggywallet.manager.db.RoomDatabaseManager
import com.example.piggywallet.manager.db.datamodel.BookMenus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * ViewModel is to Provide data to UI and serve a configure change
 * ViewModel is center between Repository and UI and can share across fragment
 * */

class BookDetailViewModel (application:Application):AndroidViewModel(application),CoroutineScope by MainScope(){
    //annotate repository that want to use to compiler know

    val whenDataOutcomeLoaded = MutableLiveData<List<BookMenusItem>>()
    val whenDataIncomeLoaded = MutableLiveData<List<BookMenusItem>>()
    private  val repository:BookMenusRepository
    val allBookMenus:LiveData<List<BookMenus>>

    init {
        //annotate Dao that want to use and get Instance of Dao from Database
        val bookmenusdao = RoomDatabaseManager.getInstance(application).masterBookMenuDao()
        repository = BookMenusRepository(bookmenusdao)
        allBookMenus = repository.allBookMenus

    }

    fun initialBookMenus(){

        val listMenu = mutableListOf(
            BookMenus(menuID = "book01" , menuName = "Food" ,menuImg = "" , menuType = "OUTCOME" ) ,
            BookMenus(menuID = "book02" , menuName = "Traffic" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book03" , menuName = "Social" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book04" , menuName = "Gift" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book05" , menuName = "Tax" ,menuImg = "" , menuType = "OUTCOME") ,
            BookMenus(menuID = "book06" , menuName = "Resident" ,menuImg = "", menuType = "OUTCOME" ),
            BookMenus(menuID = "book07" , menuName = "Commercial" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book08" , menuName = "Medical" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book09" , menuName = "Baby" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book10" , menuName = "Dress" ,menuImg = "", menuType = "OUTCOME" ),
            BookMenus(menuID = "book11" , menuName = "Travel" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book12" , menuName = "Entertainment" ,menuImg = "" , menuType = "OUTCOME"),
            BookMenus(menuID = "book13" , menuName = "Cash" ,menuImg = "" , menuType = "INCOME"),
            BookMenus(menuID = "book14" , menuName = "Bank" ,menuImg = "" , menuType = "INCOME"),
            BookMenus(menuID = "book15" , menuName = "Salary" ,menuImg = "" , menuType = "INCOME"),
            BookMenus(menuID = "book16" , menuName = "Other" ,menuImg = "", menuType = "INCOME" )
            )

        for (list in listMenu) {
            insert(list)
        }

    }

    fun insert(sleepNight: BookMenus) { // = MainScope().launch {
        GlobalScope.launch(Dispatchers.Main) {
            repository.insert(sleepNight)
        }
    }

    fun setMenusItem(item: List<BookMenus>) { // = MainScope().launch {
        if(item.size>0){
            val lisIncomeItem = item
            ?.filter { it.menuType == "INCOME" }
            ?.map {
                BookMenusItem(
                    menuID = it.menuID ,
                    menuTYPE = it.menuType ,
                    menuName = it.menuName,
                    menuIMG = it.menuImg
                )
            }

            val lisOutcomeItem = item
                ?.filter { it.menuType == "OUTCOME" }
                ?.map {
                    BookMenusItem(
                        menuID = it.menuID ,
                        menuTYPE = it.menuType ,
                        menuName = it.menuName,
                        menuIMG = it.menuImg
                    )
                }
            whenDataIncomeLoaded.postValue(lisIncomeItem!!)
            whenDataOutcomeLoaded.postValue(lisOutcomeItem!!)
        }

    }

    data class BookMenusItem(
        var menuID: String = "",
        var menuTYPE: String = "",
        var menuName: String = "",
        var menuIMG: String = "",
    )

}

