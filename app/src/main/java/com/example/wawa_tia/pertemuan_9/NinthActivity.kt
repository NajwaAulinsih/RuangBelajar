
package com.example.wawa_tia.pertemuan_9

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wawa_tia.databinding.ActivityNinthBinding

class NinthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNinthBinding

    private val blogJaringan =
        "https://blogbelajar.id/tutorial-instalasi-dan-konfigurasi-ampache-server-untuk-audio-streaming-di-ubuntu-server/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupCreateRoomButton()
        setupChipGroup()
        setupTopicButtons()
    }

    private fun setupToolbar() {
        binding.toolbarNinth.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupCreateRoomButton() {
        binding.btnCreateRoom.setOnClickListener {

            val roomName = binding.etRoomName.text.toString().trim()
            val roomDescription =
                binding.etRoomDescription.text.toString().trim()

            if (roomName.isEmpty()) {
                binding.etRoomName.error = "Nama room harus diisi"
                return@setOnClickListener
            }

            if (roomDescription.isEmpty()) {
                binding.etRoomDescription.error =
                    "Deskripsi room harus diisi"
                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Room \"$roomName\" berhasil dibuat!",
                Toast.LENGTH_SHORT
            ).show()

            binding.etRoomName.text?.clear()
            binding.etRoomDescription.text?.clear()
        }
    }

    private fun setupChipGroup() {
        binding.chipGroupSubjects.setOnCheckedStateChangeListener {
                _, checkedIds ->

            if (checkedIds.isNotEmpty()) {
                val selectedChip =
                    findViewById<com.google.android.material.chip.Chip>(
                        checkedIds[0]
                    )

                Toast.makeText(
                    this,
                    "Filter: ${selectedChip.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupTopicButtons() {

        // JARINGAN KOMPUTER
        binding.btnJaringan.setOnClickListener {
            openWebsite(blogJaringan)
        }

        // PEMROGRAMAN
        binding.btnIntent.setOnClickListener {
            openGoogleSearch("Tutorial Pemrograman Android Kotlin")
        }

        // MULTIMEDIA
        binding.btnCardView.setOnClickListener {
            openYouTubeSearch("Tutorial Multimedia dan Animasi")
        }

        // BASIS DATA
        binding.btnSQL.setOnClickListener {
            openGoogleSearch("Tutorial SQL dan Basis Data")
        }

        // ALGORITMA
        binding.btnSorting.setOnClickListener {
            openYouTubeSearch("Tutorial Algoritma dan Struktur Data")
        }

        // SISTEM INFORMASI
        binding.btnSearching.setOnClickListener {
            openGoogleSearch("Materi Sistem Informasi")
        }
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openGoogleSearch(query: String) {
        val url = "https://www.google.com/search?q=${Uri.encode(query)}"
        openWebsite(url)
    }

    private fun openYouTubeSearch(query: String) {
        val url = "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
        openWebsite(url)
    }
}
