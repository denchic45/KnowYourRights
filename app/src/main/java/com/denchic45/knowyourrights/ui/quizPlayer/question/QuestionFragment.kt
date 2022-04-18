package com.denchic45.knowyourrights.ui.quizPlayer.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuestionBinding
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.ui.quizPlayer.multiChoice.MultiChoiceFragment
import com.denchic45.knowyourrights.ui.quizPlayer.singleChoice.SingleChoiceFragment
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import kotlinx.coroutines.flow.take


class QuestionFragment :
    BaseFragment<QuestionViewModel, FragmentQuestionBinding>(R.layout.fragment_question) {

    override val viewModel: QuestionViewModel by viewModels { viewModelFactory }

    private val playerViewModel: QuizPlayerViewModel by activityViewModels()

    override val binding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            playerViewModel.currentQuestion.take(1).collectWhenStarted(lifecycleScope) { question ->
                tvQuestion.text = question.title
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
                            is Question.Choice.EnterChoice -> TODO()
                        },
                        null
                    )
                    .commit()
            }

            playerViewModel.readyAnswer.collectWhenStarted(lifecycleScope) {
                btnAnswer.isEnabled = it
            }

//            playerViewModel.lockAnswersContainer.collectWhenStarted(lifecycleScope) {
//                overlay.visibility = View.VISIBLE
//            }

            btnAnswer.setOnClickListener {
                playerViewModel.onTryAnswerClick()
                overlay.visibility = View.VISIBLE
            }
        }
    }

}