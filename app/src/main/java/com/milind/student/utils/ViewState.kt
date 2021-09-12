package com.milind.student.utils

import com.milind.student.model.StudentModel

sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val notemodel: List<StudentModel>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
