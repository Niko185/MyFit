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
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import data.ItemExercisesModel
import pl.droidsonroids.gif.GifDrawable
import ru.netology.myfit.R
import ru.netology.myfit.databinding.FragmentFourStartExerciseBinding
import tools.utils.FragmentControl
import tools.utils.TimeConvertor
import view_model.MyViewModel


class FourExerciseFragment : Fragment() {
    private lateinit var binding: FragmentFourStartExerciseBinding
    private val myViewModel: MyViewModel by activityViewModels()
    private var myList: ArrayList<ItemExercisesModel>? = null
    private var doneExercisesCounter = 0
    private var timer: CountDownTimer? = null
    private var actionBar : ActionBar? = null
    private var currentDay: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFourStartExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = myViewModel.currentDay
        doneExercisesCounter = myViewModel.getExercisesCount()
        myViewModel.mutableArrayExercises.observe(viewLifecycleOwner){
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
            FragmentControl.openFragment(FiveDoneFragment.toFix(), activity as AppCompatActivity) }

    }

    private fun showTopBlock(itemExercisesModel: ItemExercisesModel) = with(binding) {
        fFourTopImageGif.setImageDrawable(GifDrawable(root.context.assets, itemExercisesModel.gifExercises))
        fFourTopTextNameExercises.text = itemExercisesModel.nameExercises
    }

    private fun setTimeTypeExercises(itemExercisesModel: ItemExercisesModel) {
        if (itemExercisesModel.timeExercises.startsWith("x")) {
            binding.fFourCentralTime.text = itemExercisesModel.timeExercises
            timer?.cancel()
            binding.fFourCentralPBar.visibility = View.INVISIBLE
        } else {
            onTimer(itemExercisesModel)
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
    private fun convertTimeExercisesBottom(itemExercisesModel: ItemExercisesModel){
        if (itemExercisesModel.timeExercises.startsWith("x")) {
            binding.fFourBottomTextNameAndTimeNextExercises.text = "Next exercises: ${itemExercisesModel.nameExercises.toString()} ${itemExercisesModel.timeExercises.toString() }"
        } else {
            val nameAndTime = "Next exercises: " + itemExercisesModel.nameExercises + " ${TimeConvertor.getTime(itemExercisesModel.timeExercises.toLong() * 1000)}"
            binding.fFourBottomTextNameAndTimeNextExercises.text  = nameAndTime
        }
    }

    private fun textActionBar(){
        var text = getString(R.string.four_action_bar_text_one)
        val textTitle = "$text $doneExercisesCounter / ${myList?.size}"
        actionBar?.title = textTitle
    }

    private fun onTimer(itemExercisesModel: ItemExercisesModel) = with(binding) {
        fFourCentralPBar.max = itemExercisesModel.timeExercises.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(itemExercisesModel.timeExercises.toLong() * 1000, 1){
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
        myViewModel.savePreference(currentDay.toString(), doneExercisesCounter - 1)
        timer?.cancel()
    }


    companion object {
        @JvmStatic
        fun toFix() = FourExerciseFragment()
    }
}



