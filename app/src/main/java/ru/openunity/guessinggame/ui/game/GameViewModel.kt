package ru.openunity.guessinggame.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.openunity.guessinggame.data.Question
import ru.openunity.guessinggame.data.QuestionDao

class GameViewModel(dao: QuestionDao) : ViewModel() {
    private val state = MutableLiveData(GameState.LOADING)
    val allQuestions = dao.getAllActive()
    val currentQuestion = MutableLiveData(Question())
    private var initialLivesLeft = 9


    private var correctGuess = ""

    private val _secretWordDisplay: MutableLiveData<String> = MutableLiveData("")
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay

    private val _incorrectGuesses: MutableLiveData<String> = MutableLiveData("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses

    private val _livesLeft: MutableLiveData<Int> = MutableLiveData(initialLivesLeft)
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    private val _gameOver: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        initGames()
    }

    private fun initGames() {
        _incorrectGuesses.value = ""
        correctGuess = ""
        _livesLeft.value = initialLivesLeft
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        currentQuestion.value!!.answer.forEach {
            display += checkLetter(it)
        }
        return display
    }

    private fun checkLetter(char: Char) = when (correctGuess.contains(char, true)) {
        true -> char
        false -> '_'
    }

    fun updateData(questions: List<Question>?) {
        when {
            questions == null -> {
                state.value = GameState.LOADING
                currentQuestion.value = Question(
                    question = "Loading",
                    answer = ""
                )
                initGames()
            }

            questions.isEmpty() -> {
                state.value = GameState.NO_QUESTIONS
                currentQuestion.value = Question(
                    question = "Add some new questions",
                    answer = ""
                )
                initGames()
            }

            state.value != GameState.GAME -> {
                state.value = GameState.GAME
                currentQuestion.value = questions.random()
                initGames()
            }

            else -> {}
        }
    }

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message += "You won!"
        else if (isLost()) message += "You lost!"
        message += "\nThe word was ${currentQuestion.value!!.answer}"
        return message
    }

    private fun isLost(): Boolean = (_livesLeft.value ?: 0) <= 0

    private fun isWon(): Boolean =
        currentQuestion.value!!.answer.equals(_secretWordDisplay.value, true)

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (currentQuestion.value!!.answer.contains(guess, true)) {
                correctGuess += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value = (_incorrectGuesses.value ?: "") + "$guess "
                _livesLeft.value = _livesLeft.value?.dec()
            }
            if (isWon() || isLost()) _gameOver.value = true
        }
    }

    fun finishGame() {
        _gameOver.value = true
    }

    enum class GameState {
        LOADING, NO_QUESTIONS, GAME
    }
}