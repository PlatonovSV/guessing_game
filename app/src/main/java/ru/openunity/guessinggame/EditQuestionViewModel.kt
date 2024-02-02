package ru.openunity.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditQuestionViewModel(questionId: Long, val dao: QuestionDao) : ViewModel() {
    val question = dao.get(questionId)

    private val _navigateToList = MutableLiveData(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    fun updateQuestion() {
        viewModelScope.launch {
            dao.update(question.value!!)
            _navigateToList.value = true
        }
    }

    fun deleteQuestion() {
        viewModelScope.launch {
            dao.delete(question.value!!)
            _navigateToList.value = true
        }
    }

    fun cancel() {
        _navigateToList.value = true
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}