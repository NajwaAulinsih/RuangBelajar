
package com.example.wawa_tia.pertemuan_9.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wawa_tia.LoginActivity
import com.example.wawa_tia.databinding.ActivityTutorialMessageBinding
import kotlinx.coroutines.launch

class TutorialMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupViewPager()
        setupNavigationButtons()
    }

    private fun setupToolbar() {
        binding.toolbarTutorial.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        val adapter = TutorialFragmentAdapter(this)

        binding.tutorialMessageViewPager.adapter = adapter

        binding.dotIndicator.attachTo(binding.tutorialMessageViewPager)

        binding.tutorialMessageViewPager.registerOnPageChangeCallback(
            object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateButtons(position)
                }
            }
        )

        updateButtons(0)
    }

    private fun setupNavigationButtons() {
        binding.btnSkip.setOnClickListener {
            finishTutorial()
        }

        binding.btnNext.setOnClickListener {
            val currentPage = binding.tutorialMessageViewPager.currentItem

            if (currentPage < 2) {
                binding.tutorialMessageViewPager.currentItem = currentPage + 1
            } else {
                finishTutorial()
            }
        }
    }

    private fun updateButtons(position: Int) {
        binding.btnSkip.visibility =
            if (position == 2) View.GONE else View.VISIBLE

        binding.btnNext.text =
            if (position == 2) "Mulai" else "Selanjutnya"
    }

    private fun finishTutorial() {
        val sharedPref = getSharedPreferences(
            "user_pref",
            MODE_PRIVATE
        )

        sharedPref.edit()
            .putBoolean("isTutorialCompleted", true)
            .apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
