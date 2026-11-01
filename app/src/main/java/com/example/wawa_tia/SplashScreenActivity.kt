
package com.example.wawa_tia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wawa_tia.databinding.ActivitySplashScreenBinding
import com.example.wawa_tia.pertemuan_9.tutorial.TutorialMessageActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2000)

            val sharedPref = getSharedPreferences(
                "user_pref",
                MODE_PRIVATE
            )

            val isLogin = sharedPref.getBoolean("isLogin", false)
            val isTutorialCompleted = sharedPref.getBoolean(
                "isTutorialCompleted",
                false
            )

            val target = when {
                isLogin -> MainActivity::class.java

                !isTutorialCompleted -> TutorialMessageActivity::class.java

                else -> LoginActivity::class.java
            }

            startActivity(Intent(this@SplashScreenActivity, target))
            finish()
        }
    }
}
