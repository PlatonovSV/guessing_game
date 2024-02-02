package ru.openunity.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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