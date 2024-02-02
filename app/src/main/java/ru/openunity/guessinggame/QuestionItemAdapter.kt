package ru.openunity.guessinggame

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuestionItemAdapter : RecyclerView.Adapter<QuestionItemAdapter.QuestionItemViewHolder>() {
    var data = listOf<Question>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemViewHolder =
        QuestionItemViewHolder.inflateFrom(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: QuestionItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class QuestionItemViewHolder(rootView: CardView) :
        RecyclerView.ViewHolder(rootView) {
        private val questionView: TextView = rootView.findViewById(R.id.question)
        private val answerView: TextView = rootView.findViewById(R.id.answer)
        private val isActiveView: CheckBox = rootView.findViewById(R.id.is_active)

        companion object {
            fun inflateFrom(parent: ViewGroup): QuestionItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.question_item, parent, false) as CardView
                return QuestionItemViewHolder(view)
            }
        }

        fun bind(item: Question) {
            questionView.text = item.question
            answerView.text = item.answer
            isActiveView.isChecked = item.isActive
        }
    }
}