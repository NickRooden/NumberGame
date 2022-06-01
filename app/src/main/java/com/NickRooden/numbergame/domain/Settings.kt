package com.NickRooden.numbergame.domain

import java.io.Serializable

data class Settings(

    val maxSum: Int,
    val answersToWin: Int,
    val percentToWin: Int,
    val timeOfGame: Int
): Serializable
