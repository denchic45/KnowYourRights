package com.denchic45.knowyourrights.ui.quizPlayer.start

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentStartQuizBinding
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import kotlinx.coroutines.delay

class StartQuizFragment : Fragment(R.layout.fragment_start_quiz) {

    private val binding: FragmentStartQuizBinding by viewBinding(FragmentStartQuizBinding::bind)
    private val viewModel: QuizPlayerViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().lifecycleScope.launchWhenStarted {
            with(binding) {
                viewModel.quiz.collect {
                    tvQuizName.text = it.name
                    tvQuestions.text = requireContext().resources.getQuantityString(
                        R.plurals.question,
                        it.questionsCount,
                        it.questionsCount
                    )

                    delay(500)
                    tvQuizName.animate()
                        .alpha(1F)
                        .setDuration(300)
                        .start()
                    delay(300)
                    tvQuestions.translationX = -100F
                    tvQuestions.animate()
                        .alpha(1F)
                        .setDuration(300)
                        .translationX(0F)
                }
            }
        }
    }
}