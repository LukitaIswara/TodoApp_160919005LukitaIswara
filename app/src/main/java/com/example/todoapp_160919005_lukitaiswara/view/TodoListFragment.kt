package com.example.todoapp_160919005_lukitaiswara.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp_160919005_lukitaiswara.R
import com.example.todoapp_160919005_lukitaiswara.viewmodel.ListTodoViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    private lateinit var viewModel: ListTodoViewModel
    private val todoListAdapter = TodoListAdapter(arrayListOf(),{ item -> viewModel.clearTask(item) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        recToDoList.layoutManager = LinearLayoutManager(context)
        recToDoList.adapter = todoListAdapter



        floatingActionButton.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateToDo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()


    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            if(it.isEmpty()) {
                textEmpty.visibility = View.VISIBLE
            } else {
                textEmpty.visibility = View.GONE
            }
        })
    }

}