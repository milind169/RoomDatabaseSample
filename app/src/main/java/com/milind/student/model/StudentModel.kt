package com.milind.student.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Student")
@Parcelize
data class StudentModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val feedback: String,
    val stream: String,
) : Parcelable {

}
