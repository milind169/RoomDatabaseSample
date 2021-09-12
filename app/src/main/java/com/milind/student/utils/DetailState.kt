package com.milind.student.utils

import com.milind.student.model.StudentModel


sealed class DetailState {
    object Loading : DetailState()
    object Empty : DetailState()
    data class Success(val studentModel: StudentModel) : DetailState()
    data class Error(val exception: Throwable) : DetailState()
}
