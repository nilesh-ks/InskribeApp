package com.codingwithme.inskribe.adapter

import com.codingwithme.inskribe.entities.Task

interface OnTodoClickListener {
    fun onTodoClick(task: Task)
    fun onTodoRadioButtonClick(task: Task)
}