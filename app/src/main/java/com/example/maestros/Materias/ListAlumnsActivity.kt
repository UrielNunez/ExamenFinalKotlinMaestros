package com.example.maestros.Materias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.maestros.MainActivity
import com.example.maestros.R
import com.example.maestros.databinding.ActivityListAlumnsBinding
import com.example.maestros.databinding.ActivitySignInBinding

class ListAlumnsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListAlumnsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alumns)
        binding = ActivityListAlumnsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayAdapter:ArrayAdapter<*>

        val alumnos = mutableListOf("Alan Núñez", "Uriel Flores", "Roberto Macías", "Erick león", "Arturo Vallejo")
        val lvAlumnos = findViewById<ListView>(R.id.lvAlumnos)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alumnos)
        lvAlumnos.adapter = arrayAdapter

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}