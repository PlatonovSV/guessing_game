package ru.openunity.guessinggame.ui.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.openunity.guessinggame.data.Question
import ru.openunity.guessinggame.databinding.QuestionItemBinding

class QuestionItemAdapter(val clickListener: (Long) -> Unit) :
    ListAdapter<Question, QuestionItemAdapter.QuestionItemViewHolder>(QuestionDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemViewHolder =
        QuestionItemViewHolder.inflateFrom(parent)


    override fun onBindViewHolder(holder: QuestionItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class QuestionItemViewHolder(private val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): QuestionItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QuestionItemBinding.inflate(layoutInflater, parent, false)
                return QuestionItemViewHolder(binding)
            }
        }

        fun bind(item: Question, clickListener: (Long) -> Unit) {
            binding.question = item
            binding.root.setOnClickListener { clickListener(item.id) }
        }
    }
}