package com.example.wawa_tia.pertemuan_6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wawa_tia.R
import com.example.wawa_tia.databinding.ActivitySixthBinding

class SixthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySixthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySixthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            title = "RuangBelajar"
            subtitle = "Jelajahi Materi"
            setDisplayHomeAsUpEnabled(true)
        }

        // Menampilkan Fragment pertama saat halaman dibuka
        replaceFragment(SatuFragment(), false)

        binding.btnFragment1.setOnClickListener {
            replaceFragment(SatuFragment())
        }

        binding.btnFragment2.setOnClickListener {
            replaceFragment(DuaFragment())
        }

        binding.btnFragment3.setOnClickListener {
            replaceFragment(TigaFragment())
        }
    }

    private fun replaceFragment(
        fragment: androidx.fragment.app.Fragment,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
