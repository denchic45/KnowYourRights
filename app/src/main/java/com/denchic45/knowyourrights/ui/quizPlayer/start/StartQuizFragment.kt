package com.denchic45.knowyourrights.ui.quizPlayer.start

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentStartQuizBinding
import com.denchic45.kts.ui.BaseFragment

class StartQuizFragment :
    BaseFragment<StartQuizViewModel, FragmentStartQuizBinding>(R.layout.fragment_start_quiz) {
    override val binding: FragmentStartQuizBinding by viewBinding(FragmentStartQuizBinding::bind)
    override val viewModel: StartQuizViewModel by viewModels { viewModelFactory }
}