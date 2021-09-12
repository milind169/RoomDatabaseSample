package com.milind.student.view.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.milind.student.R
import com.milind.student.databinding.FragmentAddStudentBinding
import com.milind.student.model.StudentModel
import com.milind.student.view.base.BaseFragment
import com.milind.student.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_add_student_layout.view.*

@AndroidEntryPoint
class AddStudentFragment : BaseFragment<FragmentAddStudentBinding, StudentViewModel>() {

    override val viewModel: StudentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_student, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                save()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() = with(binding) {
        val name = addStudentLayout.etName.text.toString().trim()
        val feedback = addStudentLayout.etFeedback.text.toString().trim()
        val stream = addStudentLayout.etStream.text.toString().trim()

        if (name.isNotEmpty()) {
            val student = StudentModel(0, name, feedback, stream)

            viewModel.insert(student)
            toast(getString(R.string.success_saved))

            findNavController().navigate(
                R.id.action_addStudentFragment_to_dashboardFragment
            )
        } else {
            toast(getString(R.string.entername))
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddStudentBinding.inflate(inflater, container, false)
}