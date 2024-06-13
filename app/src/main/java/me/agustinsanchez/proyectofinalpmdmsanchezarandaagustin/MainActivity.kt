package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
    }//onCreate

}//class