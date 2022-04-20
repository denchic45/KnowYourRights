package com.denchic45.knowyourrights.ui.quizPlayer.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuestionBinding
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.ui.quizPlayer.enterChoice.EnterChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.multiChoice.MultiChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.singleChoice.SingleChoiceFragment
import com.denchic45.knowyourrights.utils.collectWhenStarted
import kotlinx.coroutines.flow.take


class QuestionFragment : Fragment(R.layout.fragment_question) {

    val viewModel: QuizPlayerViewModel by activityViewModels()

    val binding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.currentQuestion
                .take(1)
                .collectWhenStarted(lifecycleScope) { question ->
                    tvQuestion.text = question.title

                    ivQuestion.visibility = question.imageUrl?.let {
                        Glide.with(this@QuestionFragment)
                            .load(it)
                            .into(ivQuestion)
                        View.VISIBLE
                    } ?: View.GONE

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

            viewModel.readyAnswer.collectWhenStarted(lifecycleScope) {
                btnAnswer.isEnabled = it
            }

            btnAnswer.setOnClickListener {
                viewModel.onTryAnswerClick()
                overlay.visibility = View.VISIBLE
            }
        }
    }

}