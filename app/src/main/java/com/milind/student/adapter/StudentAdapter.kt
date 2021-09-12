package com.milind.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.milind.student.databinding.ItemsStudentBinding
import com.milind.student.model.StudentModel
import com.milind.student.view.dashboard.DashboardFragmentDirections

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            ItemsStudentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val Model = differ.currentList[position]
        holder.bind(Model)
    }

    inner class StudentViewHolder(private val itemsBinding: ItemsStudentBinding) :
        RecyclerView.ViewHolder(itemsBinding.root) {
        fun bind(studentModel: StudentModel) {
            itemsBinding.apply {
                itemsBinding.student = studentModel
                itemsBinding.executePendingBindings()

                tvStream.text = studentModel.stream
                tvStream.visibility = View.VISIBLE

                root.setOnClickListener { v ->
                    val direction = DashboardFragmentDirections
                        .actionDashboardFragmentToUpdateStudentFragment(studentModel)
                    v.findNavController().navigate(direction)
                }

            }
        }

    }

    private val differCallback =
        object : DiffUtil.ItemCallback<StudentModel>() {
            override fun areItemsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.feedback == newItem.feedback &&
                        oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}


