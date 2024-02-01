package ru.openunity.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

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

    private val questions = dao.getAll()
    val questionsString = questions.map {
        formatQuestions(it)
    }

    private fun formatQuestions(question: List<Question>): String {
        return question.fold("") {
            str, item -> str + '\n' + formatQuestion(item)
        }
    }

    private fun formatQuestion(question: Question): String {
        var str = "ID: ${question.id}"
        str += '\n' + "Question: ${question.question}"
        str += '\n' + "Answer: ${question.answer}"
        str += '\n' + "isActive: ${question.isActive}" + '\n'
        return str
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