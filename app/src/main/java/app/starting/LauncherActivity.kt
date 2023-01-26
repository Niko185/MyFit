package app.starting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import app.fragments.FirstDaysFragment
import ru.netology.myfit.databinding.ActivityLauncherBinding
import tools.utils.FragmentControl
import view_model.MyViewModel

class LauncherActivity : AppCompatActivity() {


    private val mutableModel: MyViewModel by viewModels()


    lateinit var binding: ActivityLauncherBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mutableModel.preference = getSharedPreferences("table", MODE_PRIVATE)


        FragmentControl.openFragment(FirstDaysFragment.toFix(), this@LauncherActivity)

    }

    override fun onBackPressed() {
        if(FragmentControl.openedFragment is FirstDaysFragment) super.onBackPressed()
        else FragmentControl.openFragment(FirstDaysFragment.toFix(), this)
    }
}