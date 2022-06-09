package com.example.quizy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import com.example.quizy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.edName.text?.filters = arrayOf(
            InputFilter { source, start, end, dest, dstart, dend ->
                return@InputFilter source.replace(Regex("[^a-zA-Z ]*"), "")
            }
        )*/

        binding.btn.setOnClickListener {
            if (binding.edname.text!!.isEmpty()) {
                binding.tvEnterName.error = "Please enter your Name"
                /*Toast.makeText(this, "Plase enter your name", Toast.LENGTH_SHORT).show()*/

            } else {
                var i=Intent(applicationContext, QuizQuestionActivity::class.java)
                i.putExtra(Constants.USER_NAME,binding.edname.text.toString())
                startActivity(i)
                finish()
            }
        }

    }
}