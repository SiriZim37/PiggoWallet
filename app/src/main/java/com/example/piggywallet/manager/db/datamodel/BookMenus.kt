package com.example.piggywallet.manager.db.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_menu_tb")
data class BookMenus(
    @PrimaryKey
    @ColumnInfo(name = "menu_id")
    val menuID: String ,

    @ColumnInfo(name = "menu_name")
    val menuName: String,

    @ColumnInfo(name = "menu_img")
    val menuImg: String,

    @ColumnInfo(name = "menu_type")
    val menuType: String,
)
