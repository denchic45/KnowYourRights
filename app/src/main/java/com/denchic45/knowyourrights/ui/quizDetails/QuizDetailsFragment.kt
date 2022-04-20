package com.denchic45.knowyourrights.ui.quizDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.FragmentQuizDetailsBinding
import com.denchic45.knowyourrights.ui.quizPlayer.QuizPlayerActivity
import com.denchic45.knowyourrights.utils.collectWhenStarted
import com.denchic45.kts.di.viewmodel.ViewModelFactory
import com.denchic45.kts.ui.HasNavArgs
import com.denchic45.kts.ui.HasViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class QuizDetailsFragment : BottomSheetDialogFragment(), HasViewModel<QuizDetailsViewModel>,
    HasNavArgs<QuizDetailsFragmentArgs> {

    private val binding: FragmentQuizDetailsBinding by viewBinding(FragmentQuizDetailsBinding::bind)

    override val navArgs: QuizDetailsFragmentArgs by navArgs()

    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<QuizDetailsViewModel>

    override val viewModel: QuizDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.quizDetails.collectWhenStarted(lifecycleScope) { quizItem ->
                Glide.with(requireContext())
                    .load(quizItem.imageUrl)
                    .centerCrop()
                    .into(ivQuizImage)

                tvQuizName.text = quizItem.name
                tvQuizTitleQuestionsCount.text = "${quizItem.questionsCount} вопросов"
            }
            viewModel.openQuizPlayer.collectWhenStarted(lifecycleScope) { quizId ->
                dismiss()
                startActivity(
                    Intent(requireActivity(), QuizPlayerActivity::class.java)
                        .apply { putExtra(QuizPlayerActivity.QUIZ_ID, quizId) }
                )
            }

            viewModel.openBrowser.collectWhenStarted(lifecycleScope) {
                val defaultBrowser = Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_BROWSER
                )
                defaultBrowser.data = it.toUri()
                startActivity(defaultBrowser)
            }

            btnStart.setOnClickListener { viewModel.onPlayClick() }

            btnLecture.setOnClickListener { viewModel.onLectureClick() }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        const val QUIZ_ID = "QuizDetailsFragment QUIZ_ID"
    }

}