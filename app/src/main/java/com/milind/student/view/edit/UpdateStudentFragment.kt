package com.milind.student.view.edit

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.milind.student.R
import com.milind.student.databinding.FragmentUpdateStudentBinding
import com.milind.student.model.StudentModel
import com.milind.student.view.base.BaseFragment
import com.milind.student.viewmodel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_add_student_layout.view.*

@AndroidEntryPoint
class UpdateStudentFragment : BaseFragment<FragmentUpdateStudentBinding, StudentViewModel>() {
    private val args: UpdateStudentFragmentArgs by navArgs()
    override val viewModel: StudentViewModel by activityViewModels()
    private lateinit var studentModel: StudentModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentModel = args.studentModel!!
        getdetailsStudent()

    }

    private fun getdetailsStudent() = with(binding) {
        updateStudentLayout.etFeedback.setText(studentModel.feedback)
        updateStudentLayout.etName.setText(studentModel.name)
        updateStudentLayout.etStream.setText(studentModel.stream)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_student, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_note_update -> {
                update()
            }
            R.id.action_delete -> {
                delete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete() {

        AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom).apply {
            setTitle("Delete")
            setMessage("Are you sure you want to delete this ?")
            setPositiveButton("DELETE") { _, _ ->
                viewModel.delete(studentModel)
                view?.findNavController()?.navigate(
                    R.id.action_updateStudentFragment_to_dashboardFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }

    private fun update() = with(binding) {
        val title = updateStudentLayout.etName.text.toString().trim()
        val body = updateStudentLayout.etFeedback.text.toString().trim()
        val date = updateStudentLayout.etStream.text.toString().trim()

        if (title.isNotEmpty()) {
            val student = StudentModel(studentModel.id, title, body, date)
            viewModel.update(student)

            toast(getString(R.string.success_update))

            findNavController().navigate(
                R.id.action_updateStudentFragment_to_dashboardFragment
            )
        } else {
            toast(getString(R.string.entername))
        }


    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUpdateStudentBinding.inflate(inflater, container, false)
}