package ru.openunity.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.openunity.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.livesLeft.observe(viewLifecycleOwner) {
            binding.lives.text = getString(R.string.lives_left, viewModel.livesLeft.value.toString())
        }
        viewModel.incorrectGuess.observe(viewLifecycleOwner) {
            binding.incorrectGuesses.text =
            if (viewModel.incorrectGuess.value?.isNotBlank() == true) {
                getString(R.string.incorrect_guesses, viewModel.incorrectGuess.value)
            } else ""
        }
        viewModel.secretWordDisplay.observe(viewLifecycleOwner) {
            binding.word.text = viewModel.secretWordDisplay.value
        }
        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }



        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}