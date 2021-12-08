package com.example.maestros.Alumnos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.maestros.MainActivity
import com.example.maestros.R
import com.example.maestros.databinding.ActivityListAlumnsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_alumns.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alumnos_content.view.*

class ListAlumnsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityListAlumnsBinding


    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private val listAlumnos:MutableList<Alumnos> = ArrayList()
    val myRef = database.getReference("alumnos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alumns)

        binding = ActivityListAlumnsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.newFloatingActionButtonAlumnos.setOnClickListener {
            intent = Intent(this, AddAlumnosActivity::class.java)
            startActivity(intent)
        }

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

        listAlumnos.clear()
        setupRecyclerView(alumnosRecyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listAlumnos.clear()
                dataSnapshot.children.forEach { child ->
                    val alumnos: Alumnos? =
                        Alumnos(child.child("name").getValue<String>(),
                            child.child("semestre").getValue<String>(),
                            child.child("califica").getValue<String>(),
                            child.key)
                    alumnos?.let { listAlumnos.add(it) }
                }
                recyclerView.adapter = AlumnosViewAdapter(listAlumnos)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class AlumnosViewAdapter(private val values: List<Alumnos>) :
        RecyclerView.Adapter<AlumnosViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.alumnos_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val alumnos = values[position]
            holder.mNameTextView.text = alumnos.name
            holder.mSemestreTextView.text = alumnos.semestre
            holder.mCalificaTextView.text = alumnos.califica

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, AlumnosDetailActivity::class.java).apply {
                    putExtra("key", alumnos.key)
                }
                v.context.startActivity(intent)
            }


        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.nameTextView
            val mSemestreTextView: TextView = view.semestreTextView
            val mCalificaTextView: TextView = view.calificaTextView
        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listAlumnos.get(viewHolder.adapterPosition).key?.let { myRef.child(it).setValue(null) }
                listAlumnos.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}