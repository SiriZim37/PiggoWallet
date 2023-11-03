package com.example.piggywallet.manager.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.piggywallet.manager.db.datamodel.BookMenus
import com.example.piggywallet.manager.db.datamodel.BookNote

@Dao
interface RoomDBDao {

//    BOOK MENUS
    @Insert
    fun insert(item: BookMenus)

    @Update
    fun update(item: BookMenus)

    @Query("SELECT * from book_menu_tb WHERE menu_id = :key ")
    fun get(key:Long): BookMenus?

    @Query("DELETE FROM book_menu_tb")
    fun clear()

    @Query("SELECT * FROM book_menu_tb ORDER BY menu_id DESC LIMIT 1")
    fun getBookMenus(): BookMenus?

    @Query("SELECT * FROM book_menu_tb ORDER BY menu_id ASC")
    fun getAllBookMenus(): LiveData<List<BookMenus>>

//    INCOME && OUTCOME
    @Insert
    fun insertInAndOutCome(item: BookNote)

    @Update
    fun updateInAndOutCome(item: BookNote)

    @Query("DELETE FROM book_note_tb")
    fun clearInAndOutCome()

    @Query("SELECT * FROM book_note_tb ORDER BY date  ASC")
    fun getAllInAndOutCome(): LiveData<List<BookNote>>

    @Query("SELECT * FROM book_note_tb WHERE menu_type = 'INCOME' ORDER BY date  DESC")
    fun getAllIncomeData(): LiveData<List<BookNote>>

    @Query("SELECT * FROM book_note_tb WHERE menu_type = 'OUTCOME' ORDER BY date  DESC")
    fun getAllOutComeData(): LiveData<List<BookNote>>
    @Query("SELECT SUM(total) FROM book_note_tb WHERE menu_type = 'INCOME' ORDER BY date  DESC ")
    fun getIncomeTotal(): LiveData<String>

    @Query("SELECT SUM(total) FROM book_note_tb WHERE menu_type = 'OUTCOME' ORDER BY date  DESC ")
    fun getOutComeTotal(): LiveData<String>

    @Query("SELECT * FROM book_note_tb WHERE date = :dateData ORDER BY menu_type  ASC")
    fun getInAndOutComeByDate(dateData : String): LiveData<List<BookNote>>




}
