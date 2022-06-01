package com.NickRooden.numbergame.domain

import java.io.Serializable

data class ResultsGm(

    val winner: Boolean,
    val rightAnswers: Int,
    val allAnswers: Int,
    val settings: Settings
): Serializable
