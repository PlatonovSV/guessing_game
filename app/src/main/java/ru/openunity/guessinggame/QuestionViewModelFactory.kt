package ru.openunity.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class QuestionViewModelFactory(private val dao: QuestionDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}