package ru.openunity.guessinggame.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.openunity.guessinggame.data.QuestionDao

class EditQuestionViewModel(questionId: Long, val dao: QuestionDao) : ViewModel() {
    val question = dao.get(questionId)


    fun updateQuestion() {
        viewModelScope.launch {
            dao.update(question.value!!)
        }
    }

}