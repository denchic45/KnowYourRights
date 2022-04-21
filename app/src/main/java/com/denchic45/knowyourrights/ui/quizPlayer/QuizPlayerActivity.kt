package com.denchic45.knowyourrights.ui.quizPlayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.ActivityQuizPlayerBinding
import com.denchic45.knowyourrights.ui.base.BaseActivity
import com.denchic45.knowyourrights.utils.collectWhenStarted

class QuizPlayerActivity :
    BaseActivity<QuizPlayerViewModel, ActivityQuizPlayerBinding>(R.layout.activity_quiz_player) {

    override val binding: ActivityQuizPlayerBinding by viewBinding(ActivityQuizPlayerBinding::bind)
    override val viewModel: QuizPlayerViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        getDelegate().setLocalNightMode(
            AppCompatDelegate.MODE_NIGHT_YES);

        viewModel.retryQuiz.collectWhenStarted(lifecycleScope) { quizId ->
            finish()
            startActivity(Intent(this, QuizPlayerActivity::class.java)
                .apply { putExtra(QUIZ_ID, quizId) })

        }
    }

    companion object {
        const val QUIZ_ID = "QuizPlayerActivity QUIZ_ID"
    }

}