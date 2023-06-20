package myfit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import myfit.fragments.DaysFragment
import myfit.dialogs.FragmentControl
import myfit.vm.MainViewModel
import ru.netology.myfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mutableModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mutableModel.preference = getSharedPreferences("table", MODE_PRIVATE)
        FragmentControl.openFragment(DaysFragment.toFix(), this@MainActivity)
    }

    override fun onBackPressed() {
        if(FragmentControl.openedFragment is DaysFragment) super.onBackPressed()
        else FragmentControl.openFragment(DaysFragment.toFix(), this)
    }
}