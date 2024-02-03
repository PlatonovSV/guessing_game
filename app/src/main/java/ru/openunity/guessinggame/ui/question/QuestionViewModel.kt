package ru.openunity.guessinggame.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.openunity.guessinggame.data.Question
import ru.openunity.guessinggame.data.QuestionDao

class QuestionViewModel(private val dao: QuestionDao) : ViewModel() {
    var questionText = ""
    var answer = ""
    private val _showToast: MutableLiveData<Boolean> = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    val minimumAnswerLength = 3
    val minimumQuestionLength = 10

    val questions = dao.getAll()

    private val _navigateToQuestion = MutableLiveData<Long?>()
    val navigateToQuestion: LiveData<Long?>
        get() = _navigateToQuestion


    fun onQuestionClicked(taskId: Long) {
        _navigateToQuestion.value = taskId
    }
    fun onQuestionNavigated() {
        _navigateToQuestion.value = null
    }
    fun toastShows() {
        _showToast.value = false
    }
    fun addQuestion() {
        if (
            questionText.length >= minimumQuestionLength
            && answer.length >= minimumAnswerLength
        ) {
            viewModelScope.launch {
                val question = Question(
                    question = questionText,
                    answer = answer
                )
                dao.insert(question)
            }
        } else {
            _showToast.value = true
        }
    }
}