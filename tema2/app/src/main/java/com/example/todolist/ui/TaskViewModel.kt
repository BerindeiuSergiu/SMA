package com.example.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.todolist.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val db = Room.databaseBuilder(
        app,
        TaskDatabase::class.java,
        "tasks_db"
    ).build()

    private val repo = TaskRepository(db.taskDao())

    val tasks = repo.allTasks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addTask(title: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) repo.insert(Task(title = title))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { repo.delete(task) }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch { repo.update(task.copy(isDone = !task.isDone)) }
    }
}