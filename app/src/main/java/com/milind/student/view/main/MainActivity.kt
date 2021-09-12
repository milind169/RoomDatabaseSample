package com.milind.student.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.milind.student.R
import com.milind.student.databinding.ActivityMainBinding
import com.milind.student.repository.StudentRepository
import com.milind.student.storage.db.StudentDatabase
import com.milind.student.utils.hide
import com.milind.student.utils.show
import com.milind.student.utils.viewModelFactory
import com.milind.student.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val repo by lazy { StudentRepository(StudentDatabase(this)) }
    private val viewModel: StudentViewModel by viewModels {
        viewModelFactory { StudentViewModel(this.application, repo) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel

        initViews(binding)
        observeNavElements(binding, navHostFragment.navController)
    }

    private fun observeNavElements(binding: ActivityMainBinding, navController: NavController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.dashboardFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    binding.tvToolbarTitle.show()

                }
                R.id.addStudentFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                    binding.tvToolbarTitle.hide()
                }

                R.id.updateStudentFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                    binding.tvToolbarTitle.hide()
                }

                else -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                }
            }
        }

    }

    private fun initViews(binding: ActivityMainBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}