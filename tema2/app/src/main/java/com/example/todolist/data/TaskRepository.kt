package com.example.todolist.data

class TaskRepository(private val dao: TaskDao) {
    val allTasks = dao.getAllTasks()

    suspend fun insert(task: Task) = dao.insertTask(task)
    suspend fun delete(task: Task) = dao.deleteTask(task)
    suspend fun update(task: Task) = dao.updateTask(task)
}