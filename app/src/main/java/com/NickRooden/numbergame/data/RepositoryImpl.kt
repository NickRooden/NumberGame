package com.NickRooden.numbergame.data

import com.NickRooden.numbergame.domain.Level
import com.NickRooden.numbergame.domain.Question
import com.NickRooden.numbergame.domain.Repository
import com.NickRooden.numbergame.domain.Settings
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object RepositoryImpl : Repository{

    private const val MIN_SUM = 5
    private const val MIN_SEE_NBR = 2
    private const val QUANTITY_OPTION = 6


    override fun getQuestion(maxSum: Int): Question {

        val sum = Random.nextInt(MIN_SUM, maxSum + 1)
        val numberToSee = Random.nextInt(MIN_SEE_NBR, sum - 1)
        val rightAnswer = sum - numberToSee

        val optionNbr = HashSet<Int>()
        optionNbr.add(rightAnswer)
        val from = max(MIN_SEE_NBR, rightAnswer - QUANTITY_OPTION)
        val to = min(maxSum, rightAnswer + QUANTITY_OPTION)
        while (optionNbr.size < QUANTITY_OPTION){
            optionNbr.add(Random.nextInt(from, to))
        }
        return Question(sum, numberToSee, optionNbr.toList())
    }

    override fun getSettings(level: Level): Settings {
        return when (level){
            Level.TEST -> Settings(10, 3, 50, 8)
            Level.EYSY -> Settings(10, 3, 50, 8)
            Level.NORMAL -> Settings(10, 3, 50, 8)
            Level.HARD -> Settings(10, 3, 50, 8)
        }
    }


}