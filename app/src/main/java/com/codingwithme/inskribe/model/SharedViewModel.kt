package com.codingwithme.inskribe.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithme.inskribe.entities.Task

class SharedViewModel: ViewModel() {
    private val selectedItem: MutableLiveData<Task> = MutableLiveData()
    private var isEdit: Boolean = false


    fun selectItem(task: Task)
    {
        selectedItem.value = task
    }
    fun getSelectedItem(): LiveData<Task> {
        return selectedItem
    }
    fun setIsEdit(isEdit: Boolean){
        this.isEdit = isEdit
    }

    fun getIsEdit(): Boolean{
        return isEdit
    }
}