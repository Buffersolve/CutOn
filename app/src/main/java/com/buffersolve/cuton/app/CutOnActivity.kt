package com.buffersolve.cuton.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.buffersolve.cuton.databinding.ActivityCutonBinding

class CutOnActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCutonBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCutonBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        
        
        
    }
}