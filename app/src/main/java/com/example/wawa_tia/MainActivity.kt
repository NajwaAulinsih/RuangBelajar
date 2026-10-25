
package com.example.wawa_tia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wawa_tia.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedPref by lazy {
        getSharedPreferences("user_pref", MODE_PRIVATE)
    }

    private val materialTitles = arrayOf(
        "Pemrograman Android",
        "Basis Data",
        "Algoritma & Pemrograman"
    )

    private val materialDescriptions = arrayOf(
        "ViewBinding, Intent, AlertDialog, dan CardView pada Android Studio.",
        "SQL, Relasi Tabel, dan Normalisasi Basis Data.",
        "Sorting, Searching, dan Logika Pemrograman Dasar."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = sharedPref.getString("username", "Pengguna") ?: "Pengguna"

        binding.tvGreeting.text = "Halo, $username!"
        binding.tvProfileUsername.text = "Username: $username"

        setupMaterials()
        setupRoomCards()
        setupLogout()

        binding.btnToSixth.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.example.wawa_tia.pertemuan_6.SixthActivity::class.java
                )
            )
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showPage(binding.scrollHome)
                    true
                }

                R.id.nav_material -> {
                    showPage(binding.materialContent)
                    true
                }

                R.id.nav_profile -> {
                    showPage(binding.profileContent)
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun showPage(selectedView: View) {
        binding.scrollHome.visibility = View.GONE
        binding.materialContent.visibility = View.GONE
        binding.profileContent.visibility = View.GONE

        selectedView.visibility = View.VISIBLE
    }


    private fun setupMaterials() {
        val adapter = MateriAdapter(
            this,
            materialTitles,
            materialDescriptions
        )

        binding.listMaterials.adapter = adapter

        binding.listMaterials.setOnItemClickListener { _, _, position, _ ->
            openDetail(
                "Room: ${materialTitles[position]}",
                materialDescriptions[position]
            )
        }
    }



    private fun setupRoomCards() {
        binding.cardRoom1.setOnClickListener {
            openDetail(
                "Room 1: Pemrograman Android",
                materialDescriptions[0]
            )
        }

        binding.cardRoom2.setOnClickListener {
            openDetail(
                "Room 2: Basis Data",
                materialDescriptions[1]
            )
        }

        binding.cardRoom3.setOnClickListener {
            openDetail(
                "Room 3: Algoritma & Pemrograman",
                materialDescriptions[2]
            )
        }
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Log Out")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit().clear().apply()

                    val intent = Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun openDetail(title: String, description: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_TITLE", title)
        intent.putExtra("EXTRA_DESC", description)
        startActivity(intent)
    }
}
