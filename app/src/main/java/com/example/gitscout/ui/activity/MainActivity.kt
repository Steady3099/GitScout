package com.example.gitscout.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitscout.R
import com.example.gitscout.databinding.ActivityMainBinding
import com.example.gitscout.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, SearchFragment())
                .commit()
        }
    }
}