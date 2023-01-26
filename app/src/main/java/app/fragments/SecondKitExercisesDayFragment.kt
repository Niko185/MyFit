package app.fragments

import adapters.AdapterExercises
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.myfit.R
import ru.netology.myfit.databinding.FragmentSecondKitExercisesBinding
import tools.utils.FragmentControl
import view_model.MyViewModel


class SecondKitExercisesDayFragment : Fragment() {
    private lateinit var binding : FragmentSecondKitExercisesBinding
    private var actionBarTwo: ActionBar? = null
    private val mutableModel : MyViewModel by activityViewModels()
    private lateinit var adapterEx : AdapterExercises

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondKitExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mutableModel.mutableArrayExercises.observe(viewLifecycleOwner){
            for (i in 0 until mutableModel.getExercisesCount()){
                it[i] = it[i].copy(checkBoxDone = true)
            }
            initialization()
            adapterEx.submitList(it)
            actionBarTwo = (activity as AppCompatActivity).supportActionBar
            getTextActionBar()
        }
    }

    private fun initialization() = with(binding){
        adapterEx = AdapterExercises()
        fSecondRcView.layoutManager = LinearLayoutManager(activity)
        fSecondRcView.adapter = adapterEx
        fSecondButton.setOnClickListener{
            FragmentControl.openFragment(ThreeTimerFragment.toFix(), activity as AppCompatActivity)
        }


    }

    private fun getTextActionBar(){
        val text = getString(R.string.second_action_bar_text)
        actionBarTwo?.title = text
    }


    companion object {
        @JvmStatic
        fun toFix() = SecondKitExercisesDayFragment()
    }

}

