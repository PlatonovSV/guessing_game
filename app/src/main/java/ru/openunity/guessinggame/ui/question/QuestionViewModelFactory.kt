package ru.openunity.guessinggame.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.openunity.guessinggame.data.QuestionDao
import java.lang.IllegalArgumentException

class QuestionViewModelFactory(private val dao: QuestionDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}