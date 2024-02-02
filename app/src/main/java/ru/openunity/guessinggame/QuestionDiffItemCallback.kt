package ru.openunity.guessinggame

import androidx.recyclerview.widget.DiffUtil

class QuestionDiffItemCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
        (oldItem == newItem)
}