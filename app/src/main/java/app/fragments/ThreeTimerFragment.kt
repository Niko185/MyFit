package app.fragments


import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ru.netology.myfit.R
import ru.netology.myfit.databinding.FragmentThreeTimerBinding
import tools.utils.FragmentControl
import tools.utils.TimeConvertor


const val TIME_TIMER = 11000L

class ThreeTimerFragment : Fragment() {
    private lateinit var binding : FragmentThreeTimerBinding
    private lateinit var timer: CountDownTimer
    private var actionBarThree: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeTimerBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fThreeProgBar.max = TIME_TIMER.toInt()
        onTimer()
        actionBarThree = (activity as AppCompatActivity).supportActionBar
        getTextActionBarThree()

    }

    private fun onTimer() = with(binding) {
        timer = object : CountDownTimer(TIME_TIMER, 1){
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTick(time: Long) {
                fThreeTimer.text = TimeConvertor.getTime(time)
                fThreeProgBar.progress = time.toInt()
            }

            override fun onFinish() {
                FragmentControl.openFragment(FourExerciseFragment.toFix(), activity as AppCompatActivity)
            }

        }.start()
    }

    private fun getTextActionBarThree(){
        var text = getString(R.string.three_action_bar_text)
        actionBarThree?.title = text
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }
    companion object {
        @JvmStatic
        fun toFix() = ThreeTimerFragment()
    }

}

