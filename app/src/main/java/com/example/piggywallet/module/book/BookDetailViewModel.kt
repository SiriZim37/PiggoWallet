package com.example.piggywallet.module.book


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.piggywallet.manager.db.RoomDBRepository
import com.example.piggywallet.manager.db.RoomDatabaseManager
import com.example.piggywallet.manager.db.datamodel.BookMenus
import com.example.piggywallet.manager.db.datamodel.BookNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

/**
 * ViewModel is to Provide data to UI and serve a configure change
 * ViewModel is center between Repository and UI and can share across fragment
 * */

class BookDetailViewModel (application:Application):AndroidViewModel(application),CoroutineScope by MainScope(){
    //annotate repository that want to use to compiler know

    val whenDataOutcomeLoaded = MutableLiveData<List<BookMenusItem>>()
    val whenDataIncomeLoaded = MutableLiveData<List<BookMenusItem>>()
    private  val repository:RoomDBRepository
    val allBookMenus:LiveData<List<BookMenus>>


    init {
        //annotate Dao that want to use and get Instance of Dao from Database
        RoomDatabaseManager.getInstance(application)
        val bookmenusdao = RoomDatabaseManager.getInstance(application).roomDBDao()
        repository = RoomDBRepository(bookmenusdao)
        allBookMenus = repository.allBookMenus

//        val inoutdao = RoomDatabaseManager.getInstance(application).masterInAndOutComeDao()
//        repository = RoomDBRepository(inoutdao)
//        allBookMenus = repository.allInOutData
//


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

    fun insert(menus: BookMenus) { // = MainScope().launch {
        GlobalScope.launch(Dispatchers.Main) {
            repository.insert(menus)
        }
    }

    fun setMenusItem(item: List<BookMenus>) { // = MainScope().launch {
//        val dataItem = DatabaseManager.getInstance().getBooksMenu()
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
            }?.sortedBy { it.menuID }

            val lisOutcomeItem = item
                ?.filter { it.menuType == "OUTCOME" }
                ?.map {
                    BookMenusItem(
                        menuID = it.menuID ,
                        menuTYPE = it.menuType ,
                        menuName = it.menuName,
                        menuIMG = it.menuImg
                    )
                }?.sortedBy { it.menuID }
            whenDataIncomeLoaded.postValue(lisIncomeItem!!)
            whenDataOutcomeLoaded.postValue(lisOutcomeItem!!)
        }
    }


    fun saveData( menuID : String , menuName : String , total : String , des : String , menuType : String){
        try {

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())

            val data = BookNote(
                seq = UUID.randomUUID().toString(),
                menuID = menuID ,
                menuName = menuName ,
                menuType = menuType ,
                total = total ,
                des = des ,
                date = currentDate.toString())

            insertInOutcome ( data )


        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun insertInOutcome(menus: BookNote) { // = MainScope().launch {
        GlobalScope.launch(Dispatchers.Main) {
            repository.insertInAndOutCome(menus)
        }
    }

    data class BookMenusItem(
        var menuID: String = "",
        var menuTYPE: String = "",
        var menuName: String = "",
        var menuIMG: String = "",
    )

    data class BookNoteItem(
        var menuID: String = "",
        var menuTYPE: String,
        var menuName: String = "",
        var total: String = "0.00",
        var description: String,
        var date : String
    )

}

