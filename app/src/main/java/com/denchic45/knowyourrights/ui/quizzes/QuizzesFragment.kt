package com.denchic45.knowyourrights.ui.quizzes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuizzesBinding
import com.denchic45.knowyourrights.ui.adapter.QuizItemAdapterDelegate
import com.denchic45.kts.ui.BaseFragment
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.widget.extendedAdapter.adapter

class QuizzesFragment :
    BaseFragment<QuizzesViewModel, FragmentQuizzesBinding>(R.layout.fragment_quizzes) {

    override val binding: FragmentQuizzesBinding by viewBinding(FragmentQuizzesBinding::bind)
    override val viewModel: QuizzesViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = adapter {
            delegates(QuizItemAdapterDelegate())
            onClick { viewModel.onQuizItemClick(it) }
        }
        binding.rv.adapter = adapter
        viewModel.quizzes.collectWhenStarted(lifecycleScope) {
            Log.d("lol", "onViewCreated submit: ${it.size}")
            adapter. submit(it)
        }
    }
}