package app.starting

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

import ru.netology.myfit.databinding.ActivitySplashScreenBinding



@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashTimer: CountDownTimer
    lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashTimer = object : CountDownTimer(2000, 1000){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashScreenActivity, LauncherActivity::class.java))
            }

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        splashTimer.cancel()
    }
}