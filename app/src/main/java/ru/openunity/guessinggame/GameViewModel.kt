package ru.openunity.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val words = listOf("Android", "Activity", "Fragment")
    private val secretWord = words.random().uppercase()
    private var correctGuess = ""

    private val _secretWordDisplay: MutableLiveData<String> = MutableLiveData()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    private val _incorrectGuesses: MutableLiveData<String> = MutableLiveData("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft:MutableLiveData<Int> = MutableLiveData(9)
    val livesLeft: LiveData<Int>
        get() = _livesLeft
    private val _gameOver:MutableLiveData<Boolean> = MutableLiveData(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it)
        }
        return display
    }

    private fun checkLetter(char: Char) = when (correctGuess.contains(char, true)) {
        true -> char
        false -> '_'
    }

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message += "You won!"
        else if (isLost()) message += "You lost!"
        message += "\nThe word was $secretWord"
        return message
    }

    private fun isLost(): Boolean = (_livesLeft.value ?: 0) <= 0

    private fun isWon(): Boolean = secretWord.equals(_secretWordDisplay.value, true)

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
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
}