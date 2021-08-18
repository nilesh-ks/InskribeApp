package com.codingwithme.inskribe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingwithme.inskribe.adapter.OnTodoClickListener
import com.codingwithme.inskribe.adapter.RecyclerViewAdapter
import com.codingwithme.inskribe.model.SharedViewModel
import com.codingwithme.inskribe.entities.Task
import com.codingwithme.inskribe.model.TaskViewModel
import com.codingwithme.inskribe.util.BottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_todo.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoActivity : AppCompatActivity(), OnTodoClickListener {
    companion object{
        private val TAG: String = "ITEM"
    }

    var arrTodo = ArrayList<Task>()
    lateinit var currDate: TextView
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    //private lateinit var recyclerViewAdapter2: RecyclerViewAdapter
    private var counter: Int=0
    lateinit var bottomSheetFragment: BottomSheetFragment
    lateinit var constraintLayout: ConstraintLayout
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        bottomSheetFragment= BottomSheetFragment()
        constraintLayout=findViewById(R.id.bottomSheet)
        bottomSheetBehavior= BottomSheetBehavior.from(constraintLayout)
        bottomSheetBehavior.isHideable=true
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN)
        recyclerView=findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(this)

        taskViewModel= ViewModelProvider.AndroidViewModelFactory(
            this@TodoActivity.application)
            .create(TaskViewModel::class.java)
        //The declaration provides all the
        // TaskViewModel methods to the variable here

        sharedViewModel = ViewModelProvider(this)
            .get(SharedViewModel::class.java)

        taskViewModel.getAllTasks().observe(this,{tasks->
//            run {
//                for (task: Task in tasks) {
//                    Log.d(TAG, "onCreate: " + task.taskId)
//                }
//            }

            recyclerViewAdapter=RecyclerViewAdapter(tasks, this)
            recyclerView.adapter=recyclerViewAdapter

        })

        currDate=findViewById(R.id.dateToday)
        val cal = Calendar.getInstance().time
        var sdf: SimpleDateFormat = SimpleDateFormat("MMM dd, yyyy")
        currDate.text=sdf.format(cal).toString()



        val fab = findViewById<Button>(R.id.fab)
        fab.setOnClickListener { view ->
//            val task: Task = Task("Task "+counter++,Priority.HIGH,Calendar.getInstance().time,
//                Calendar.getInstance().time, false)
//            TaskViewModel.insert(task)
            showBottomSheetDialog()
        }


       /* search_view_todo.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Task>()

                for (arr in arrTodo){
                    if (arr.task!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                recyclerViewAdapter2.setData(tempArr)
                recyclerViewAdapter2.notifyDataSetChanged()
                return true
            }

        })*/

        fabNotes.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showBottomSheetDialog() {
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag) //tag is given to every fragment that we create
    }

    /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.menu_main, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml.
         val id = item.itemId
         return if (id == R.id.action_settings) {
             true
         } else super.onOptionsItemSelected(item)
     }*/

    override fun onTodoClick( task: Task) {
        //Log.d("Click", "onTodoClick: " + adapterPosition+" "+task.task)
        sharedViewModel.selectItem(task)
        sharedViewModel.setIsEdit(true)
        showBottomSheetDialog()

    }

    override fun onTodoRadioButtonClick(task: Task) {
        Log.d("Click", "onRadioButton: " +task.task)
        TaskViewModel.delete(task)
        recyclerViewAdapter.notifyDataSetChanged()
    }


}