package ru.openunity.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.openunity.guessinggame.databinding.FragmentEditQuestionBinding


class EditQuestionFragment : Fragment() {
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
        val view = binding.root
        viewModel.navigateToList.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                view.findNavController().navigate(R.id.action_editQuestionFragment_to_questionsFragment)
                viewModel.onNavigatedToList()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}