package com.milind.student.view.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.milind.student.R
import com.milind.student.adapter.StudentAdapter
import com.milind.student.databinding.FragmentDashboardBinding
import com.milind.student.model.StudentModel
import com.milind.student.utils.hide
import com.milind.student.utils.show
import com.milind.student.view.base.BaseFragment
import com.milind.student.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, StudentViewModel>() {
    private lateinit var customAdapter: StudentAdapter
    override val viewModel: StudentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        inits()

    }

    private fun setupRV() {
        customAdapter = StudentAdapter()
        rv_dashboard.apply {
            setHasFixedSize(true)
            adapter = customAdapter

            activity.let {
                viewModel.getAll().observe(viewLifecycleOwner, { student ->
                    customAdapter.differ.submitList(student)
                    updateUI(student)
                })
            }

        }
    }

    private fun updateUI(student: List<StudentModel>) {
        if (student.isNotEmpty()) {
            layout_empty_dashboard.hide()
            rv_dashboard.visibility = View.VISIBLE
        } else {
            layout_empty_dashboard.show()
            rv_dashboard.visibility = View.GONE
        }

    }


    private fun inits() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addStudentFragment)
        }

        mainDashboardScrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 12 && btnAdd.isExtended) {
                btnAdd.shrink()
            }

            if (scrollY < oldScrollY - 12 && !btnAdd.isExtended) {
                btnAdd.extend()
            }

            if (scrollY == 0) {
                btnAdd.extend()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

        lifecycleScope.launchWhenStarted {
            val isChecked = viewModel.getUIMode.first()
            val uiMode = menu.findItem(R.id.action_night_mode)
            uiMode.isChecked = isChecked
            setUIMode(uiMode, isChecked)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_night_mode -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.saveToDataStore(true)
            item.setIcon(R.drawable.ic_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.saveToDataStore(false)
            item.setIcon(R.drawable.ic_day)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

}