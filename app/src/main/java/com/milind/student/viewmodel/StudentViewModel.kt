package com.milind.student.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.milind.student.model.StudentModel
import com.milind.student.repository.StudentRepository
import com.milind.student.storage.datastore.UIModeDataStore
import com.milind.student.utils.DetailState
import com.milind.student.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    application: Application,
    private val studentRepository: StudentRepository
) :
    AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)

    // UI collect from this stateFlow to get the state updates
    val uiState: StateFlow<ViewState> = _uiState
    val detailState: StateFlow<DetailState> = _detailState

    private val uiModeDataStore = UIModeDataStore(application)

    // get ui mode
    val getUIMode = uiModeDataStore.uiMode

    // save ui mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiModeDataStore.saveToDataStore(isNightMode)
        }
    }

    // insert
    fun insert(studentModel: StudentModel) = viewModelScope.launch {
        studentRepository.insert(studentModel)
    }

    // update
    fun update(studentModel: StudentModel) = viewModelScope.launch {
        studentRepository.update(studentModel)
    }

    // delete
    fun delete(studentModel: StudentModel) = viewModelScope.launch {
        studentRepository.delete(studentModel)
    }

    //get all
    fun getAll() = studentRepository.getAll()

    //get all search note
//    fun getAllSearchNote(query: String?) = studentRepository.getAllSearchNote(query)

}