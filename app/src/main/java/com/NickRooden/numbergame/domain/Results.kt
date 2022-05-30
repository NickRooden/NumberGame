package com.NickRooden.numbergame.domain

data class Results(

    val winner: Boolean,
    val rightAnswers: Int,
    val allAnswers: Int,
    val settings: Settings
)
