package com.denchic45.knowyourrights.ui.quizPlayer.multiChoice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentChoiceContainerBinding
import com.denchic45.knowyourrights.ui.adapter.MultiChoiceAdapterDelegate
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import com.denchic45.widget.extendedAdapter.adapter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class MultiChoiceFragment :
    BaseFragment<MultiChoiceViewModel, FragmentChoiceContainerBinding>(R.layout.fragment_choice_container) {

    override val binding: FragmentChoiceContainerBinding by viewBinding(
        FragmentChoiceContainerBinding::bind
    )

    override val viewModel: MultiChoiceViewModel by viewModels { viewModelFactory }

    private val playerViewModel: QuizPlayerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.root) {
            val answersAdapter = adapter {
                delegates(MultiChoiceAdapterDelegate(true))
                onClick { position ->
                    viewModel.onAnswerItemSelect(position)
                    lifecycleScope.launchWhenStarted {
                        playerViewModel.onMultiAnswerSelect(viewModel.selectedItems)
                    }
                }
            }

            adapter = answersAdapter

            playerViewModel.currentQuestion.take(1).collectWhenStarted(lifecycleScope) {
                viewModel.onQuestionLoad(it)
            }

            viewModel.answers.collectWhenStarted(lifecycleScope) {
                answersAdapter.submit(it)
            }

            playerViewModel.showActualAnswer
                .take(1)
                .map { it as QuizPlayerViewModel.ActualAnswer.MultiActualAnswer }
                .collectWhenStarted(lifecycleScope) {

                    it.correctAnswerPositions.forEach {
                        answersAdapter.notifyItemChanged(it,  MultiChoiceAdapterDelegate.PAYLOAD_TRUE)
                    }

                    it.wrongAnswerPositions.forEach {
                        answersAdapter.notifyItemChanged(it,  MultiChoiceAdapterDelegate.PAYLOAD_FALSE)
                    }
                }
        }
    }

}