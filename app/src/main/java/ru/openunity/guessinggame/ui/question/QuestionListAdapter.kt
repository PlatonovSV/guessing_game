package ru.openunity.guessinggame.ui.question

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.openunity.guessinggame.R
import ru.openunity.guessinggame.data.Question

class QuestionListAdapter(
    private var onEdit: (Question) -> Unit,
    private var onDelete: (Question) -> Unit
) : ListAdapter<Question, QuestionListAdapter.QuestionListViewHolder>(QuestionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListViewHolder {
        return QuestionListViewHolder(
            ComposeView(parent.context),
            onEdit,
            onDelete
        )
    }

    override fun onBindViewHolder(holder: QuestionListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class QuestionListViewHolder(
        private val composeView: ComposeView,
        private val onEdit: (Question) -> Unit,
        private val onDelete: (Question) -> Unit
    ) : RecyclerView.ViewHolder(composeView) {

        fun bind(input: Question) {
            composeView.setContent {
                ListItem(
                    input,
                    onDelete,
                    modifier = Modifier
                        .clickable {
                            onEdit(input)
                        }
                        .padding(vertical = 8.dp, horizontal = 8.dp)

                )
            }
        }
    }
}

@Composable
fun ListItem(
    input: Question,
    onDelete: (Question) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuestionDetails(input, Modifier.weight(1f))
            DeleteButton(
                onDelete = {
                    onDelete(input)
                },
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

@Composable
fun QuestionDetails(question: Question, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.Top) {
        Text(question.question)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = question.answer,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun DeleteButton(onDelete: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { onDelete() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.delete)
        )
    }
}

class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }
}