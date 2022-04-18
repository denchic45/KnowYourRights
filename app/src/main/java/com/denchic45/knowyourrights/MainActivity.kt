package com.denchic45.knowyourrights

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denchic45.knowyourrights.databinding.ActivityMainBinding
import com.denchic45.knowyourrights.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    override val viewModel: MainViewModel by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_quiz_results, R.id.navigation_notifications
            )
        )

        NavigationUI.setupWithNavController(
            binding.collapsingToolbar,
            binding.toolbar,
            navController,
            appBarConfiguration
        )

        binding.navView.setupWithNavController(navController)
    }
}