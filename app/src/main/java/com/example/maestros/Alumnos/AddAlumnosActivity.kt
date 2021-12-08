package com.example.maestros.Alumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.maestros.Materias.Materias
import com.example.maestros.R
import com.example.maestros.databinding.ActivityAddAlumnosBinding
import com.example.maestros.databinding.ActivityAddBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.horaEditText
import kotlinx.android.synthetic.main.activity_add.nameEditText
import kotlinx.android.synthetic.main.activity_add.saveButton
import kotlinx.android.synthetic.main.activity_add_alumnos.*

class AddAlumnosActivity : AppCompatActivity() {

    private val database = Firebase.database
    private lateinit var binding: ActivityAddAlumnosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alumnos)

        binding = ActivityAddAlumnosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myRef = database.getReference("alumnos")

        val name=nameEditText.text
        val semestre=semestreEditText.text
        val califica=calificaEditText.text

        saveButton.setOnClickListener {

            when {
                name.isEmpty() || semestre.isEmpty() || califica.isEmpty() -> {
                    Toast.makeText(baseContext, "Llena todos los campos",
                        Toast.LENGTH_SHORT).show()
                } else -> {
                val alumnos = Alumnos(
                    name.toString(),
                    semestre.toString(),
                    califica.toString())
                myRef.child(myRef.push().key.toString()).setValue(alumnos)
                finish()
            }
            }
        }
    }
}