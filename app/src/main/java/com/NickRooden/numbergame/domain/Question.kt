package com.NickRooden.numbergame.domain

data class Question(

    val sum: Int,
    val numberToSee: Int,
    val nmbrToChoice: List<Int>


){
    val rightAnswer get() = sum - numberToSee
}

