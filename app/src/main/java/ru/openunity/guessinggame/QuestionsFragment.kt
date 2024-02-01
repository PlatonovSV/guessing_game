package ru.openunity.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.openunity.guessinggame.databinding.FragmentQuestionsBinding

class QuestionsFragment : Fragment() {
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).questionDao
        val viewModelFactory = QuestionViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[QuestionViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.showToast.observe(viewLifecycleOwner) { newValue ->
            val questionLess = viewModel.questionText.length < viewModel.minimumQuestionLength
            val answerLess = viewModel.answer.length < viewModel.minimumAnswerLength
            if (newValue) {
                val toastText = when {
                    questionLess && !answerLess ->
                        getString(
                            R.string.the_minimum_length_of_the_question_is_characters,
                            viewModel.minimumQuestionLength
                        )

                    !questionLess && answerLess ->
                        getString(
                            R.string.the_minimum_answer_length_is_characters,
                            viewModel.minimumAnswerLength
                        )

                    else -> getString(
                        R.string.the_minimum_answer_length_is_characters_and_for_the_question,
                        viewModel.minimumAnswerLength,
                        viewModel.minimumQuestionLength
                    )
                }
                Toast.makeText(activity, toastText, Toast.LENGTH_LONG).show()
                viewModel.toastShows()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = binding
    }
}