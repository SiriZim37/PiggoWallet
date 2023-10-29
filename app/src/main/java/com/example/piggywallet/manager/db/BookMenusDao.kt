package com.example.piggywallet.manager.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.piggywallet.manager.db.datamodel.BookMenus

@Dao
interface BookMenusDao {

//   @Query("SELECT * FROM book_menu_table ORDER BY menu_id ASC")
//   fun getAlphabetizedWords(): Flow<List<BookMenus>>

//   @Insert(onConflict = OnConflictStrategy.IGNORE)
//   suspend fun insert(masterbookmenu: BookMenus)

//   @Query("DELETE FROM book_menu_table")
//   suspend fun deleteAll()

    @Insert
    fun insert(item: BookMenus)

    @Update
    fun update(item: BookMenus)

    @Query("SELECT * from book_menu_table WHERE menu_id = :key ")
    fun get(key:Long): BookMenus?

    @Query("DELETE FROM book_menu_table")
    fun clear()

    @Query("SELECT * FROM book_menu_table ORDER BY menu_id DESC LIMIT 1")
    fun getTonight(): BookMenus?

    @Query("SELECT * FROM book_menu_table ORDER BY menu_id DESC")
    fun getAllBookMenus(): LiveData<List<BookMenus>>


}
