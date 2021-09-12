package com.milind.student.repository


import com.milind.student.model.StudentModel
import com.milind.student.storage.db.StudentDatabase
import javax.inject.Inject

class StudentRepository @Inject constructor(private val db: StudentDatabase) {

    // insert
    suspend fun insert(studentModel: StudentModel) = db.getStudentDao().insertStudent(
        studentModel
    )

    // update
    suspend fun update(studentModel: StudentModel) = db.getStudentDao().udatateStudent(
        studentModel
    )

    // delete
    suspend fun delete(studentModel: StudentModel) = db.getStudentDao().deleteStudent(
        studentModel
    )

    fun getAll() = db.getStudentDao().getAllStudents()

    fun getAllSearchNote(query: String?) = db.getStudentDao().getsearchStudents(query)

}