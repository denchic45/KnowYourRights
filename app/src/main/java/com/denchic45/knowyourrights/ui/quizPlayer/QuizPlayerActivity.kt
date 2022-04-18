package com.denchic45.knowyourrights.ui.quizPlayer

import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.ActivityQuizPlayerBinding
import com.denchic45.knowyourrights.ui.base.BaseActivity

class QuizPlayerActivity :
    BaseActivity<QuizPlayerViewModel, ActivityQuizPlayerBinding>(R.layout.activity_quiz_player) {

    override val binding: ActivityQuizPlayerBinding by viewBinding(ActivityQuizPlayerBinding::bind)
    override val viewModel: QuizPlayerViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setSupportActionBar(binding.toolbar)
    }

    companion object {
        const val QUIZ_ID = "QuizPlayerActivity QUIZ_ID"
    }

}