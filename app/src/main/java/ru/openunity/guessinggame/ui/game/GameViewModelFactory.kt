package ru.openunity.guessinggame.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.openunity.guessinggame.data.QuestionDao
import java.lang.IllegalArgumentException

class GameViewModelFactory (private val dao: QuestionDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}