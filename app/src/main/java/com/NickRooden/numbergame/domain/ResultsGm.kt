package com.NickRooden.numbergame.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultsGm(

    val winner: Boolean,
    val rightAnswers: Int,
    val allAnswers: Int,
    val settings: Settings
): Parcelable
