package ru.openunity.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuestionViewModel(private val dao: QuestionDao) : ViewModel() {
    var questionText = ""
    var answer = ""
    private val _showToast: MutableLiveData<Boolean> = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    val minimumAnswerLength = 3
    val minimumQuestionLength = 10

    val questions = dao.getAll()


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