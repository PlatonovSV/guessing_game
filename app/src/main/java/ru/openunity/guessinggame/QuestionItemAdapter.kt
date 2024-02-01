package ru.openunity.guessinggame

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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

    class QuestionItemViewHolder(private val rootView: LinearLayout) : RecyclerView.ViewHolder(rootView) {
        companion object {
            fun inflateFrom(parent: ViewGroup): QuestionItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.question_item, parent, false) as LinearLayout
                return QuestionItemViewHolder(view)
            }
        }
        fun bind(item: Question) {
            val question = rootView.findViewById<TextView>(R.id.question)
            question.text = item.question
            val answer = rootView.findViewById<TextView>(R.id.answer)
            answer.text = item.answer


        }
    }
}