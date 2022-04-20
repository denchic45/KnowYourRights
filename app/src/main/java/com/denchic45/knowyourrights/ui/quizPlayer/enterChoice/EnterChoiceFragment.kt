package com.denchic45.knowyourrights.ui.quizPlayer.enterChoice

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.ItemEnterChoiceBinding
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class EnterChoiceFragment :
    BaseFragment<EnterChoiceViewModel, ItemEnterChoiceBinding>(R.layout.item_enter_choice) {

    override val binding: ItemEnterChoiceBinding by viewBinding(
        ItemEnterChoiceBinding::bind
    )

    override val viewModel: EnterChoiceViewModel by viewModels { viewModelFactory }

    private val playerViewModel: QuizPlayerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.et) {
           addTextChangedListener {
                playerViewModel.onEnterAnswerSelect(text.toString())
            }

//            val answersAdapter = adapter {
//                delegates(EnterChoiceAdapterDelegate())
//                onClick { position ->
////                    viewModel.onAnswerItemSelect(position)
//                    lifecycleScope.launchWhenStarted {
//
//                    }
//                }
//            }

//            adapter = answersAdapter

//            playerViewModel.currentQuestion.take(1).collectWhenStarted(lifecycleScope) {
//                viewModel.onQuestionLoad(it)
//            }

//            viewModel.answers.collectWhenStarted(lifecycleScope) {
//                answersAdapter.submit(it)
//            }

            playerViewModel.showActualAnswer
                .take(1)
                .map { it as QuizPlayerViewModel.ActualAnswer.EnterActualAnswer }
                .collectWhenStarted(lifecycleScope) { actualAnswer ->
                    if (actualAnswer.correct) {
                        binding.til.boxStrokeColor = Color.GREEN
                    } else {
                        binding.til.error = actualAnswer.correctAnswer
                    }

//                    answersAdapter.notifyItemChanged(
//                        actualAnswer.correctAnswerPosition,
//                        SingleChoiceAdapterDelegate.PAYLOAD_TRUE
//                    )
//                    actualAnswer.wrongAnswerPosition?.let {
//                        answersAdapter.notifyItemChanged(
//                            actualAnswer.wrongAnswerPosition,
//                            SingleChoiceAdapterDelegate.PAYLOAD_FALSE
//                        )
//
//
//                    }
                }
        }

    }
}