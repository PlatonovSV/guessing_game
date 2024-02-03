package ru.openunity.guessinggame.ui.question

import androidx.recyclerview.widget.DiffUtil
import ru.openunity.guessinggame.data.Question

class QuestionDiffItemCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
        (oldItem == newItem)
}