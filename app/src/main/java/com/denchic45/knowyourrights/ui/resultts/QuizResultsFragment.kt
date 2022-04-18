package com.denchic45.knowyourrights.ui.resultts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuizResultBinding
import com.denchic45.knowyourrights.ui.adapter.QuizResultAdapterDelegate
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import com.denchic45.widget.extendedAdapter.adapter

class QuizResultsFragment : BaseFragment<QuizResultsViewModel, FragmentQuizResultBinding>(
    R.layout.fragment_quiz_results
) {

    override val binding: FragmentQuizResultBinding by viewBinding(FragmentQuizResultBinding::bind)
    override val viewModel: QuizResultsViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val adapter = adapter {
                delegates(QuizResultAdapterDelegate())
                onClick { position -> viewModel.onResultItemClick(position) }
            }
            root.adapter = adapter

            viewModel.results.collectWhenStarted(lifecycleScope) {
                adapter.submit(it)
            }
        }
    }
}