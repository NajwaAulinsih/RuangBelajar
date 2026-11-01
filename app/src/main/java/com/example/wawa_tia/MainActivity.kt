
package com.example.wawa_tia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wawa_tia.databinding.ActivityMainBinding
import com.example.wawa_tia.pertemuan_9.NinthActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedPref by lazy {
        getSharedPreferences("user_pref", MODE_PRIVATE)
    }

    private val materialTitles = arrayOf(
        "Jaringan Komputer",
        "Pemrograman Android",
        "Basis Data",
        "Algoritma & Pemrograman"
    )

    private val materialDescriptions = arrayOf(
        "Tutorial jaringan komputer, server, dan konfigurasi jaringan.",
        "ViewBinding, Intent, AlertDialog, dan CardView pada Android Studio.",
        "SQL, Relasi Tabel, dan Normalisasi Basis Data.",
        "Sorting, Searching, dan Logika Pemrograman Dasar."
    )

    private val materialUrls = arrayOf(
        "https://blogbelajar.id/tutorial-instalasi-dan-konfigurasi-ampache-server-untuk-audio-streaming-di-ubuntu-server/",
        "https://www.google.com/search?q=tutorial+pemrograman+Android+Kotlin",
        "https://www.google.com/search?q=tutorial+SQL+dan+basis+data",
        "https://www.youtube.com/results?search_query=tutorial+algoritma+dan+pemrograman"
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
        setupRoomFilter()
        setupLogout()

        // Navigasi Pertemuan 6
        binding.btnToSixth.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.example.wawa_tia.pertemuan_6.SixthActivity::class.java
                )
            )
        }

        // Navigasi Praktikum 9
        binding.btnToNinth.setOnClickListener {
            startActivity(Intent(this, NinthActivity::class.java))
        }

        // Bottom Navigation
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

    // DAFTAR MATERI DAN SUMBER BELAJAR
    private fun setupMaterials() {

        val adapter = MateriAdapter(
            this,
            materialTitles,
            materialDescriptions
        )

        binding.listMaterials.adapter = adapter

        binding.listMaterials.setOnItemClickListener { _, _, position, _ ->
            openWebsite(materialUrls[position])
        }
    }

    // MEMBUKA WEBSITE
    private fun openWebsite(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Tidak dapat membuka website.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // KARTU ROOM BELAJAR
    private fun setupRoomCards() {

        binding.cardRoom1.setOnClickListener {
            openDetail(
                "Room 1: Pemrograman Android",
                "ViewBinding, Intent, AlertDialog, dan CardView pada Android Studio."
            )
        }

        binding.cardRoom2.setOnClickListener {
            openDetail(
                "Room 2: Basis Data",
                "SQL, Relasi Tabel, dan Normalisasi Basis Data."
            )
        }

        binding.cardRoom3.setOnClickListener {
            openDetail(
                "Room 3: Algoritma & Pemrograman",
                "Sorting, Searching, dan Logika Pemrograman Dasar."
            )
        }
    }

    // FILTER ROOM MENGGUNAKAN CHIPGROUP
    private fun setupRoomFilter() {

        binding.chipGroupRoom.setOnCheckedStateChangeListener { _, checkedIds ->

            binding.cardRoom1.visibility = View.GONE
            binding.cardRoom2.visibility = View.GONE
            binding.cardRoom3.visibility = View.GONE

            when (checkedIds.firstOrNull()) {

                R.id.chipSemua -> {
                    binding.cardRoom1.visibility = View.VISIBLE
                    binding.cardRoom2.visibility = View.VISIBLE
                    binding.cardRoom3.visibility = View.VISIBLE
                }

                R.id.chipAndroid -> {
                    binding.cardRoom1.visibility = View.VISIBLE
                }

                R.id.chipBasisData -> {
                    binding.cardRoom2.visibility = View.VISIBLE
                }

                R.id.chipAlgoritma -> {
                    binding.cardRoom3.visibility = View.VISIBLE
                }
            }
        }
    }

    // LOG OUT
    private fun setupLogout() {

        binding.btnLogout.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Log Out")
                .setMessage("Apakah kamu yakin ingin keluar?")

                .setPositiveButton("Ya") { dialog, _ ->

                    sharedPref.edit().clear().apply()

                    val intent = Intent(
                        this,
                        LoginActivity::class.java
                    ).apply {
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

    // MEMBUKA DETAIL ROOM
    private fun openDetail(title: String, description: String) {

        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("EXTRA_TITLE", title)
        intent.putExtra("EXTRA_DESC", description)

        startActivity(intent)
    }
}
