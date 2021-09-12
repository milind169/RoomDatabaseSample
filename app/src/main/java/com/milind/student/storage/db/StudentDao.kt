package com.milind.student.storage.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.milind.student.model.StudentModel


@Dao
interface StudentDao {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(studentModel: StudentModel)

    // update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun udatateStudent(studentModel: StudentModel)

    // delete n
    @Delete
    suspend fun deleteStudent(studentModel: StudentModel)

    // get all saved list
    @Query("SELECT * FROM student ORDER BY id DESC")
    fun getAllStudents(): LiveData<List<StudentModel>>

    @Query("SELECT * FROM student WHERE name LIKE :query OR feedback LIKE:query")
    fun getsearchStudents(query: String?): LiveData<List<StudentModel>>

}