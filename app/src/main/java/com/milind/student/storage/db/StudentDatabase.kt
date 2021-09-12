package com.milind.student.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.milind.student.model.StudentModel


@Database(entities = [StudentModel::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun getStudentDao(): StudentDao

    companion object {
        @Volatile
        private var instance: StudentDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                StudentDatabase::class.java,
                "student_db"
            ).build()
    }

}