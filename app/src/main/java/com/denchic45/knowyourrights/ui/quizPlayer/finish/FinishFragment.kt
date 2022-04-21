package com.denchic45.knowyourrights.ui.quizPlayer.finish

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentFinishBinding
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import com.denchic45.kts.ui.HasNavArgs

class FinishFragment : BaseFragment<FinishViewModel, FragmentFinishBinding>(
    R.layout.fragment_finish
), HasNavArgs<FinishFragmentArgs> {

    override val navArgs: FinishFragmentArgs by navArgs()

    override val binding: FragmentFinishBinding by viewBinding(FragmentFinishBinding::bind)

    override val viewModel: FinishViewModel by viewModels { viewModelFactory }

    private val playerViewModel: QuizPlayerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            btnBack.setOnClickListener { requireActivity().finish() }

            btnRetry.setOnClickListener { playerViewModel.onRetryClick() }

            viewModel.result.collectWhenStarted(lifecycleScope) {
                tvFinishQuizResult.text = "Вы ответили правильно на ${
                    resources.getQuantityString(
                        R.plurals.question,
                        it.yourMaxResult,
                        it.yourMaxResult
                    )
                } из ${it.questionsCount}"
            }
        }
    }

    companion object {
        const val QUIZ_ID = "FinishFragment QUIZ_ID"
    }

}