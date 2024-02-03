package ru.openunity.guessinggame.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.openunity.guessinggame.data.QuestionDao
import java.lang.IllegalArgumentException

class EditQuestionViewModelFactory(
    private val questionId: Long,
    private val dao: QuestionDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditQuestionViewModel::class.java)) {
            return EditQuestionViewModel(questionId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}