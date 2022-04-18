package com.denchic45.knowyourrights.ui.quizPlayer.finish

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentFinishBinding
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerViewModel
import com.denchic45.kts.ui.BaseFragment

class FinishFragment : BaseFragment<FinishViewModel, FragmentFinishBinding>(
    R.layout.fragment_finish
) {

    override val binding: FragmentFinishBinding by viewBinding(FragmentFinishBinding::bind)

    override val viewModel: FinishViewModel by viewModels { viewModelFactory }

    private val playerViewModel: QuizPlayerViewModel by activityViewModels()



}