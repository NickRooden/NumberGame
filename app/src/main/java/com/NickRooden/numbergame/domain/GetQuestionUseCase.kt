package com.NickRooden.numbergame.domain

class GetQuestionUseCase(
    private val repository: Repository
) {
    operator fun invoke(maxSum: Int): Question{

        return repository.getQuestion(maxSum)
    }
}