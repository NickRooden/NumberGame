package com.NickRooden.numbergame.domain

interface Repository{

    fun getQuestion(maxSum: Int): Question
    fun getSettings(level: Level): Settings
}