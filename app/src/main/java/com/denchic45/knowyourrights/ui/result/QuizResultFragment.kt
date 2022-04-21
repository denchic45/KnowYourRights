package com.denchic45.knowyourrights.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuizResultBinding
import com.denchic45.knowyourrights.ui.adapter.AnswerAdapterDelegate
import com.denchic45.knowyourrights.utils.collectWhenResumed
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.ui.BaseFragment
import com.denchic45.kts.ui.HasNavArgs
import com.denchic45.widget.extendedAdapter.adapter
import com.google.android.material.appbar.CollapsingToolbarLayout

class QuizResultFragment : BaseFragment<QuizResultViewModel, FragmentQuizResultBinding>(
    R.layout.fragment_quiz_result
), HasNavArgs<QuizResultFragmentArgs> {

    override val navArgs: QuizResultFragmentArgs by navArgs()

    override val binding: FragmentQuizResultBinding by viewBinding(FragmentQuizResultBinding::bind)

    override val viewModel: QuizResultViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val adapter = adapter {
                delegates((AnswerAdapterDelegate()))
            }
            rv.adapter = adapter
            viewModel.result.collectWhenStarted(lifecycleScope) {
                adapter.submit(it.passedQuestions)
            }
        }
    }

    override fun collectOnShowToolbarTitle() {
        super.collectOnShowToolbarTitle()
        viewModel.showToolbarTitle.collectWhenResumed(lifecycleScope) {
            requireActivity()
                .findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
                .title = it
        }
    }

    companion object {
        const val QUIZ_RESULT_ID = "QUIZ_RESULT_ID"
    }
}
