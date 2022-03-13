package com.example.bizomapplication.view.activities

/**
 * @author Appolla Sreedhar
 * @since (13/03/2022)
 * */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bizomapplication.databinding.BizomMainActivityBinding
import com.example.bizomapplication.viewmodel.BizomViewModel

/**
 * This is MainActivity and it is launcher for this application
 * */
class BizomActivity : AppCompatActivity() {
    private lateinit var binding: BizomMainActivityBinding
    lateinit var viewModel: BizomViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BizomMainActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[BizomViewModel::class.java]
        viewModel.setupData(this)
        setContentView(binding.root)
    }
}