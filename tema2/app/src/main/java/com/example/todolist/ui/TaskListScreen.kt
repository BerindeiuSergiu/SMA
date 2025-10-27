package com.example.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("To-Do List", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Row {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFECECEC), shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addTask(text)
                text = ""
            }) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = { viewModel.toggleTask(task) }
                    )
                    Text(
                        text = task.title,
                        color = if (task.isDone) Color.Gray else Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { viewModel.deleteTask(task) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
                Divider()
            }
        }
    }
}