package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    init {
        Log.d("Game Fragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Game Fragment", "GameViewModel destroyed!")
    }

    /**
     * Update currentWord and currentScrambledWord with the next word
     */
    fun getNextWord() {
        currentWord = allWordsList.random()

        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while(tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if ( wordsList.contains( tempWord.toString() ) ) {
            getNextWord()
        }  else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /**
     * returns true if the current word count is less then MAX_NO_OF_WORDS
     * Update the next word
     * */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(userWord: String): Boolean {
        return if (userWord.equals(currentWord, true)) {
            increaseScore()
             true
        } else {
            false
        }
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}