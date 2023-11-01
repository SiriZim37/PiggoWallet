package com.example.piggywallet.manager.db.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "book_note_tb")
data class BookNote(

    @PrimaryKey
    @ColumnInfo(name = "seq_id")
    val seq: String ,

    @ColumnInfo(name = "menu_id")
    val menuID: String,

    @ColumnInfo(name = "menu_type")
    val menuType: String,

    @ColumnInfo(name = "menu_name")
    val menuName: String,

    @ColumnInfo(name = "total")
    val total: String,

    @ColumnInfo(name = "description")
    val des: String,

    @ColumnInfo(name = "date")
    val date: String

)