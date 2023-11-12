package com.example.piggywallet.manager.db.datamodel

data class User(
    val userUid:String = "",
    val userName: String = "",
    val userImage:String = "",
    val userEmail:String = "",
    val userAddress:String = "",
    val userPhone: String = ""
)