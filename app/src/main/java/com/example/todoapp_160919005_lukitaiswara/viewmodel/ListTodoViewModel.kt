package com.example.todoapp_160919005_lukitaiswara.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoapp_160919005_lukitaiswara.model.Todo
import com.example.todoapp_160919005_lukitaiswara.model.TodoDatabase
import com.example.todoapp_160919005_lukitaiswara.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
    fun clearTask(todo: Todo) {
        launch {
            val db = buildDB(getApplication())

            db.todoDao().updateIsDone(todo.uuid)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

}

