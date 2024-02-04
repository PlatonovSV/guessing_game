package ru.openunity.guessinggame.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.openunity.guessinggame.data.QuestionDatabase
import ru.openunity.guessinggame.databinding.FragmentEditQuestionBinding


class EditQuestionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentEditQuestionBinding? = null
    val binding: FragmentEditQuestionBinding
        get() = _binding!!

    lateinit var viewModel: EditQuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditQuestionBinding.inflate(layoutInflater, container, false)
        val questionId = EditQuestionFragmentArgs.fromBundle(requireArguments()).questionId
        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).questionDao
        val viewModelFactory = EditQuestionViewModelFactory(questionId, dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditQuestionViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.questionText.doOnTextChanged { _, start, _, count ->
            // Enable Save button if the current text is longer than 10 characters
            binding.saveButton.isEnabled = (start+count) > 10
        }
        binding.answerText.doOnTextChanged { _, start, _, count ->
            // Enable Save button if the current text is longer than 3 characters
            binding.saveButton.isEnabled = (start+count) > 3
        }
        binding.saveButton.setOnClickListener {
            viewModel.updateQuestion()
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.deleteButton.setOnClickListener {
            viewModel.deleteQuestion()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}