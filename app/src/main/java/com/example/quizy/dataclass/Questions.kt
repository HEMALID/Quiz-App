package com.example.quizy.dataclass

data class Questions(
    val id:Int,
    val question:String,
    val image:Int,
    val option1:String,
    val option2:String,
    val option3:String,
    val option4:String,
    var answer:Int
)
