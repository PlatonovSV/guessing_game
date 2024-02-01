package ru.openunity.guessinggame

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {
    @Insert
    fun insert(question: Question)
    @Update
    fun update(question: Question)

    @Delete
    fun delete(question: Question)

    @Query("SELECT * FROM question_table WHERE id = :questionId")
    fun get(questionId:Long): LiveData<Question>

    @Query("SELECT * FROM question_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Question>>
}