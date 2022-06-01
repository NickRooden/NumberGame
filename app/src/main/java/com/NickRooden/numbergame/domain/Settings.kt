package com.NickRooden.numbergame.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(

    val maxSum: Int,
    val answersToWin: Int,
    val percentToWin: Int,
    val timeOfGame: Int
): Parcelable
