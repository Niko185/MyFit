package myfit.fragments


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
import androidx.fragment.app.activityViewModels
import myfit.models.Exercise
import pl.droidsonroids.gif.GifDrawable
import ru.netology.myfit.R
import myfit.dialogs.FragmentControl
import myfit.dialogs.TimeConvertor
import myfit.vm.MainViewModel
import ru.netology.myfit.databinding.FragmentExerciseBinding


class ExerciseFragment : Fragment() {
    private lateinit var binding: FragmentExerciseBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private var myList: ArrayList<Exercise>? = null
    private var doneExercisesCounter = 0
    private var timer: CountDownTimer? = null
    private var actionBar : ActionBar? = null
    private var currentDay: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = mainViewModel.currentDay
        doneExercisesCounter = mainViewModel.getExercisesCount()
        mainViewModel.mutableArrayExercises.observe(viewLifecycleOwner){
            myList = it
            switchNextExercises()
            actionBar  = (activity as AppCompatActivity).supportActionBar
            textActionBar()
        }
        binding.fFourButton.setOnClickListener{ switchNextExercises() }
    }

    private fun switchNextExercises() {
        if(doneExercisesCounter < myList?.size!!){
            val item = myList?.get(doneExercisesCounter++) ?: return
            showTopBlock(item)
            setTimeTypeExercises(item)
            bottomBlock()
            textActionBar()
        } else{
            doneExercisesCounter++
            FragmentControl.openFragment(CongratulationsFragment.toFix(), activity as AppCompatActivity) }
    }

    private fun showTopBlock(exercise: Exercise) = with(binding) {
        fFourTopImageGif.setImageDrawable(GifDrawable(root.context.assets, exercise.gifExercises))
        fFourTopTextNameExercises.text = exercise.nameExercises
    }

    private fun setTimeTypeExercises(exercise: Exercise) {
        if (exercise.timeExercises.startsWith("x")) {
            binding.fFourCentralTime.text = exercise.timeExercises
            timer?.cancel()
            binding.fFourCentralPBar.visibility = View.INVISIBLE
        } else {
            onTimer(exercise)
            binding.fFourCentralPBar.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun bottomBlock() = with(binding) {
        if(doneExercisesCounter < myList?.size!!){
            val item = myList?.get(doneExercisesCounter) ?: return
            fFourBottomImageGif.setImageDrawable(GifDrawable(root.context.assets, item.gifExercises))
            convertTimeExercisesBottom(item)

        } else{
            fFourBottomImageGif.setImageDrawable(GifDrawable(root.context.assets, "great.gif"))
            fFourBottomTextNameAndTimeNextExercises.text = getString(R.string.four_text_bottom)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun convertTimeExercisesBottom(exercise: Exercise){
        if (exercise.timeExercises.startsWith("x")) {
            binding.fFourBottomTextNameAndTimeNextExercises.text = "Next exercises: ${exercise.nameExercises.toString()} ${exercise.timeExercises.toString() }"
        } else {
            val nameAndTime = "Next exercises: " + exercise.nameExercises + " ${TimeConvertor.getTime(exercise.timeExercises.toLong() * 1000)}"
            binding.fFourBottomTextNameAndTimeNextExercises.text  = nameAndTime
        }
    }

    private fun textActionBar(){
        var text = getString(R.string.four_action_bar_text_one)
        val textTitle = "$text $doneExercisesCounter / ${myList?.size}"
        actionBar?.title = textTitle
    }

    private fun onTimer(exercise: Exercise) = with(binding) {
        fFourCentralPBar.max = exercise.timeExercises.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.timeExercises.toLong() * 1000, 1){
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTick(time: Long) {
                fFourCentralTime.text = TimeConvertor.getTime(time)
                fFourCentralPBar.progress = time.toInt()
            }

            override fun onFinish() {
                switchNextExercises()
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.savePreference(currentDay.toString(), doneExercisesCounter - 1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun toFix() = ExerciseFragment()
    }
}



