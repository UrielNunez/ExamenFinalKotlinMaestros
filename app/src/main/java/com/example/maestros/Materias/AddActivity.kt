package com.example.maestros.Materias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.maestros.R
import com.example.maestros.databinding.ActivityAddBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private val database = Firebase.database
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val myRef = database.getReference("materias")

        val name=nameEditText.text
        val date=dateEditText.text
        val hora=horaEditText.text
        val description=descriptionEditText.text
        val url=urlEditText.text

        saveButton.setOnClickListener {

            when {
                name.isEmpty() || date.isEmpty() || hora.isEmpty() || description.isEmpty() || url.isEmpty() -> {
                    Toast.makeText(baseContext, "Llena todos los campos",
                        Toast.LENGTH_SHORT).show()
                } else -> {
                val materias = Materias(
                    name.toString(),
                    date.toString(),
                    hora.toString(),
                    description.toString(),
                    url.toString())
                myRef.child(myRef.push().key.toString()).setValue(materias)
                finish()
            }
            }
        }
    }
}