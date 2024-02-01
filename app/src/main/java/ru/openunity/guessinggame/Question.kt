package ru.openunity.guessinggame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "question")
    var question: String = "",
    @ColumnInfo(name = "answer")
    var answer: Boolean = true
)