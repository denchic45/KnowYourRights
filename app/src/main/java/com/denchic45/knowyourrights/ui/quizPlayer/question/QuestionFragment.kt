package com.denchic45.knowyourrights.ui.quizPlayer.question

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuestionBinding
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.ui.quizPlayer.enterChoice.EnterChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.multiChoice.MultiChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.singleChoice.SingleChoiceFragment
import com.denchic45.knowyourrights.utils.collectWhenCreated
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.take


class QuestionFragment : Fragment(R.layout.fragment_question) {

    val playerViewModel: QuizPlayerViewModel by activityViewModels()

    val viewModel: QuestionViewModel by viewModels()

    val binding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            playerViewModel.currentQuestion
                .take(1)
                .collectWhenStarted(lifecycleScope) { question ->
                    tvQuestion.text = question.title

                    question.imageUrl?.let {
                        Glide.with(this@QuestionFragment)
                            .load(it)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(ivQuestion)
                        View.VISIBLE
                    } ?: run {
                        ivQuestion.visibility = View.GONE
                    }

                    parentFragmentManager.beginTransaction()
                        .add(
                            R.id.f_choice_container,
                            when (question.choice) {
                                is Question.Choice.SingleChoice -> {
                                    SingleChoiceFragment::class.java
                                }
                                is Question.Choice.MultiChoice -> {
                                    MultiChoiceFragment::class.java
                                }
                                is Question.Choice.EnterChoice -> {
                                    EnterChoiceFragment::class.java
                                }
                            },
                            null
                        )
                        .commit()
                }

            playerViewModel.dialog.collectWhenStarted(lifecycleScope) { (title, message) ->
                MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
                    .setTitle(title)
                    .setOnDismissListener { requireActivity().finish() }
                    .setMessage(null)
                    .setPositiveButton("Ok", null)
                    .create()
                    .show()
            }

            playerViewModel.readyAnswer.collectWhenStarted(lifecycleScope) {
                btnAnswer.isEnabled = it
            }

            viewModel.timeLeft.collectWhenCreated(lifecycleScope) {
                progressQuestion.progress = it
                Log.d("lol", "time left: $it")
            }

            btnAnswer.setOnClickListener {
                playerViewModel.onTryAnswerClick()
                overlay.visibility = View.VISIBLE
            }
        }
    }

}